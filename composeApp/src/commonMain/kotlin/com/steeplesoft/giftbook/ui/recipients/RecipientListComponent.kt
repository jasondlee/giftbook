package com.steeplesoft.giftbook.ui.recipients

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.Recipient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipientListComponent(componentContext: ComponentContext): ComponentContext by componentContext, KoinComponent {
    private val recipientDao : RecipientDao by inject()
    var recipients = MutableValue(listOf<Recipient>())
    var requestStatus  = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                requestStatus.update { Status.LOADING }

                val list = recipientDao.getAll()
                recipients.update { list }

                requestStatus.update { Status.SUCCESS }
            }
        }
    }
}
