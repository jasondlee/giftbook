package com.steeplesoft.giftbook.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionProgress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeComponent(
    componentContext: ComponentContext,
    var occasionId: Long? = null
) : ComponentContext by componentContext, KoinComponent {
    private val giftIdeaDao: GiftIdeaDao by inject()
    private val occasionDao: OccasionDao by inject()
    private val recipientDao: RecipientDao by inject()
    private val nav: StackNavigation<NavigationConfig> by inject()

    var occasions = MutableValue(listOf<Occasion>())
    var requestStatus = MutableValue(Status.LOADING)
    var occasionProgress: MutableValue<List<OccasionProgress>> = MutableValue(mutableListOf())
    var occasion: Occasion? = null
    var hasRecipients = false

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                requestStatus.update { Status.LOADING }

                val list = occasionDao.getFutureOccasions()
                hasRecipients = !recipientDao.getAll().isEmpty()
                occasion =
                    if (occasionId != null)
                        occasionDao.getOccasion(occasionId!!)
                    else
                        list.firstOrNull()

                occasions.update { list }

                occasion?.let {
                    onOccasionChange(it)
                }

                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun onOccasionChange(newValue: Occasion) {
        CoroutineScope(Dispatchers.IO).launch {
            occasion = newValue
            val list = recipientDao.getRecipientsForOccasion(newValue.id).map {
                val ideas = giftIdeaDao.lookupIdeasByRecipAndOccasion(it.recipientId, newValue.id)
                OccasionProgress(
                    recipientDao.getRecipient(it.recipientId),
                    newValue.id,
                    targetCount = it.targetCount,
                    actualCount = ideas.filter { idea -> idea.occasionId != null }.size,
                    actualCost = ideas.sumOf { idea -> idea.actualCost ?: 0 },
                    targetCost = it.targetCost
                )
            }

            occasionProgress.update { list }
        }
    }

    fun addRecipient() {
        occasion?.let {
            nav.bringToFront(NavigationConfig.AddEditOccasionRecipient(it))
        }
    }
}

