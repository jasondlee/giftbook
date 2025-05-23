package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewOccasionComponent(
    componentContext: ComponentContext,
    val occasion: Occasion
) : ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val occasionDao : OccasionDao by inject()
    private val recipientDao : RecipientDao by inject()

    var recips : List<Recipient> = emptyList()
    var requestStatus = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                recips = recipientDao.getRecipientListForOccasion(occasion.id)
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

    fun addRecipient() {
        nav.pushToFront(NavigationConfig.AddEditOccasionRecipient(occasion))
    }
}
