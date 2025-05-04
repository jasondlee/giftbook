package com.steeplesoft.giftbook.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface HomeComponent {
    var occasion: Occasion?
    val occasions: MutableValue<List<Occasion>>
    var requestStatus : MutableValue<Status>
    var occasionProgress: MutableValue<List<OccasionProgress>>

    fun onOccasionChange(newValue: Occasion)
}

class DefaultHomeComponent(
    componentContext: ComponentContext
) : HomeComponent,
    ComponentContext by componentContext,
    KoinComponent {
    private val giftIdeaDao : GiftIdeaDao by inject()
    private val occasionDao : OccasionDao by inject()
    private val recipientDao : RecipientDao by inject()

    override var occasion: Occasion? = null
    override var occasions = MutableValue(listOf<Occasion>())
    override var requestStatus  = MutableValue(Status.LOADING)
    override var occasionProgress: MutableValue<List<OccasionProgress>> = MutableValue(mutableListOf())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val list = occasionDao.getFutureOccasions()
            occasions.update { list }

            if (list.isNotEmpty()) {
                onOccasionChange(list[0])
            }

            requestStatus.update { Status.SUCCESS }
        }
    }

    override fun onOccasionChange(newValue: Occasion) {
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
}

@Serializable
data class OccasionProgress(
    val recipient: Recipient,
    val occasionId: Long,
    val targetCount: Int,
    val actualCount: Int,
    val targetCost: Int,
    val actualCost: Int
)
