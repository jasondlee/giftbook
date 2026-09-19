package com.steeplesoft.giftbook.ui.occasionRecip

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.componentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewOccasionRecipient(
    componentContext: ComponentContext,
    private val recipId: Long,
    private val occasionId: Long
) : ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val occasionDao : OccasionDao by inject()
    private val recipientDao : RecipientDao by inject()
    private val giftIdeaDao : GiftIdeaDao by inject()

    val occasionRecip = MutableValue(LoadedOccasionRecipient())
    val recip = MutableValue(LoadedRecipient())
    val occasion = MutableValue(LoadedOccasion())

    val gifts: MutableValue<List<GiftIdea>> = MutableValue(emptyList())
    val requestStatus: MutableValue<Status> = MutableValue(Status.LOADING)
    private val scope = componentContext.componentScope()

    init {
        componentContext.doOnResume {
            scope.launch(Dispatchers.IO) {
                val loadedOccasionRecip = recipientDao.getRecipientForOccasion(occasionId, recipId)
                val loadedRecipient = recipientDao.getRecipient(recipId)
                val loadedOccasion = occasionDao.getOccasion(occasionId)
                occasionRecip.update { LoadedOccasionRecipient(loadedOccasionRecip) }
                recip.update { LoadedRecipient(loadedRecipient) }
                occasion.update { LoadedOccasion(loadedOccasion) }
                val list = giftIdeaDao.lookupIdeasByRecipAndOccasion(recipId, occasionId)
                gifts.update { list }
                requestStatus.update { Status.SUCCESS }
            }
        }
    }


    fun edit() {
        val currentOccasion = occasion.value.value ?: return
        val currentRecipient = recip.value.value ?: return
        val currentOccasionRecip = occasionRecip.value.value ?: return
        nav.pushToFront(NavigationConfig.AddEditOccasionRecipient(currentOccasion, currentRecipient, currentOccasionRecip))
    }

    fun delete() {
        scope.launch {
            occasionRecip.value.value?.let { occasionDao.deleteOccasionRecip(it) }
            nav.pop()
        }
    }

    fun giftGiven(giftId: Long, cost: Int) {
        scope.launch(Dispatchers.IO) {
            val orig = gifts.value
            val gift = orig.firstOrNull { it.id == giftId } ?: return@launch
            val updatedGift = gift.copy(occasionId = occasionId, actualCost = cost)

            giftIdeaDao.update(updatedGift)
            gifts.update { it.map { current -> if (current.id == giftId) updatedGift else current } }
        }
    }

    fun resetGiftGiven(giftId: Long) {
        scope.launch(Dispatchers.IO) {
            val orig = gifts.value
            val gift = orig.firstOrNull { it.id == giftId } ?: return@launch
            val updatedGift = gift.copy(occasionId = null, actualCost = null)

            giftIdeaDao.update(updatedGift)
            gifts.update { it.map { current -> if (current.id == giftId) updatedGift else current } }
        }
    }

    fun addIdea() {
        recip.value.value?.let { nav.pushToFront(NavigationConfig.AddEditIdea(it)) }
    }
}

data class LoadedOccasionRecipient(val value: OccasionRecipient? = null)

data class LoadedRecipient(val value: Recipient? = null)

data class LoadedOccasion(val value: Occasion? = null)
