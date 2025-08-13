package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.kmpform.components.Status
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
    lateinit var occasion: Occasion

    var recips : MutableList<Recipient> = mutableListOf()
    var requestStatus = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                occasion = occasionDao.getOccasion(occasionId)
                recips = recipientDao.getRecipientListForOccasion(occasion.id).toMutableList()
                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun edit() {
        nav.pushToFront(NavigationConfig.AddEditOccasion(occasion))
    }

    fun delete() {
        CoroutineScope(Dispatchers.Main).launch {
            occasionDao.delete(occasion)

            nav.pop()
        }
    }

    fun deleteRecip(recip: Recipient) {
        CoroutineScope(Dispatchers.Main).launch {
            requestStatus.update { Status.LOADING }
            occasionDao.deleteOccasionRecip(recipientDao.getRecipientForOccasion(occasion.id, recip.id))
            recips.remove(recip)
            requestStatus.update { Status.SUCCESS }

        }
    }

    fun addRecipient() {
        nav.bringToFront(NavigationConfig.AddEditOccasionRecipient(occasion))
    }

    fun editOccasionRecipient(recipient: Recipient) {
        nav.bringToFront(NavigationConfig.AddEditOccasionRecipient(occasion, recipient))
    }
}
