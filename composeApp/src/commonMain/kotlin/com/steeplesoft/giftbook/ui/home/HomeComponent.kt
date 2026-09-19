package com.steeplesoft.giftbook.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushToFront
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
import com.steeplesoft.giftbook.ui.componentScope
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

    val occasions = MutableValue(listOf<Occasion>())
    val requestStatus = MutableValue(Status.LOADING)
    val occasionProgress: MutableValue<List<OccasionProgress>> = MutableValue(emptyList())
    val occasion = MutableValue<Occasion?>(null)
    private val scope = componentContext.componentScope()

    init {
        componentContext.doOnResume {
            scope.launch(Dispatchers.IO) {
                requestStatus.update { Status.LOADING }

                val list = occasionDao.getFutureOccasions()
                val selectedOccasion =
                    if (occasionId != null)
                        occasionDao.getOccasion(occasionId!!)
                    else
                        list.firstOrNull()

                occasions.update { list }
                occasion.update { selectedOccasion }

                selectedOccasion?.let {
                    onOccasionChange(it)
                }

                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun onOccasionChange(newValue: Occasion) {
        scope.launch(Dispatchers.IO) {
            occasion.update { newValue }
            val list = recipientDao.getRecipientsForOccasion(newValue.id).map { occasionRecipient ->
                val ideas = giftIdeaDao.lookupIdeasByRecipAndOccasion(occasionRecipient.recipientId, newValue.id)
                OccasionProgress(
                    recipient = recipientDao.getRecipient(occasionRecipient.recipientId),
                    occasionId = newValue.id,
                    targetCount = occasionRecipient.targetCount,
                    actualCount = ideas.count { it.occasionId != null },
                    actualCost = ideas.sumOf { it.actualCost ?: 0 },
                    targetCost = occasionRecipient.targetCost
                )
            }

            occasionProgress.update { list }
        }
    }

    fun addRecipient() {
        occasion?.let {
            nav.pushToFront(NavigationConfig.AddEditOccasionRecipient(it))
        }
    }
}
