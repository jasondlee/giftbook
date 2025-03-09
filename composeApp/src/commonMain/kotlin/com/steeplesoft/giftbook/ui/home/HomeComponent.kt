package com.steeplesoft.giftbook.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.general.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

interface HomeComponent {
    val occasion: Occasion?
    val occasions: List<Occasion>
    var requestStatus : MutableValue<Status>
    var occasionProgress: MutableValue<List<OccasionProgress>>

    fun onOccasionChange(newValue: Occasion)
}

class DefaultHomeComponent(
    componentContext: ComponentContext
) : HomeComponent,
    ComponentContext by componentContext {
    override val occasion: Occasion? = null
    override var occasions = emptyList<Occasion>()
    override var requestStatus  = MutableValue(Status.LOADING)
    override var occasionProgress: MutableValue<List<OccasionProgress>> = MutableValue(mutableListOf())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            occasions = db.occasionDao().getFutureOccasions()

            requestStatus.update { Status.SUCCESS }
        }
    }

    override fun onOccasionChange(changed: Occasion) {
        db.getCoroutineScope().launch {
            val dao = db.recipientDao()
            val list = dao.getRecipientsForOccasion(changed.id).map {
                val recipient = dao.getRecipient(it.recipientId)
                val ideas = db.giftIdeaDao().getCurrentGiftIdeasForRecipAndOccasion(it.recipientId, changed.id)
                val progress = OccasionProgress(
                    recipient,
                    changed.id,
                    targetCount = it.targetCount,
                    actualCount = ideas.filter { idea -> idea.occasionId != null }.size,
                    actualCost = ideas.map { idea -> idea.actualCost ?: 0f }.sum(),
                    targetCost = it.targetCost
                )
                progress
            }

            occasionProgress.update { list }
        }
    }
}

@Serializable
data class OccasionProgress(
    val recipient: Recipient,
    val occasionId: Int,
    val targetCount: Int,
    val actualCount: Int,
    val targetCost: Float,
    val actualCost: Float
)
