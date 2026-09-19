package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.componentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewOccasionComponent(
    componentContext: ComponentContext,
    private val occasionId: Long
) : ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val occasionDao : OccasionDao by inject()
    private val recipientDao : RecipientDao by inject()
    val occasion = MutableValue<Occasion?>(null)
    val recips = MutableValue<List<Recipient>>(emptyList())
    val requestStatus = MutableValue(Status.LOADING)
    private val scope = componentContext.componentScope()

    init {
        componentContext.doOnResume {
            scope.launch(Dispatchers.IO) {
                val loadedOccasion = occasionDao.getOccasion(occasionId)
                occasion.update { loadedOccasion }
                recips.update { recipientDao.getRecipientListForOccasion(loadedOccasion.id) }
                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun edit() {
        occasion.value?.let { nav.pushToFront(NavigationConfig.AddEditOccasion(it)) }
    }

    fun delete() {
        scope.launch {
            occasion.value?.let { occasionDao.delete(it) }
            nav.pop()
        }
    }

    fun deleteRecip(recip: Recipient) {
        scope.launch {
            requestStatus.update { Status.LOADING }
            val loadedOccasion = occasion.value ?: return@launch
            occasionDao.deleteOccasionRecip(recipientDao.getRecipientForOccasion(loadedOccasion.id, recip.id))
            recips.update { it.filterNot { current -> current.id == recip.id } }
            requestStatus.update { Status.SUCCESS }
        }
    }

    fun addRecipient() {
        occasion.value?.let { nav.bringToFront(NavigationConfig.AddEditOccasionRecipient(it)) }
    }

    fun editOccasionRecipient(recipient: Recipient) {
        occasion.value?.let { nav.bringToFront(NavigationConfig.AddEditOccasionRecipient(it, recipient)) }
    }
}
