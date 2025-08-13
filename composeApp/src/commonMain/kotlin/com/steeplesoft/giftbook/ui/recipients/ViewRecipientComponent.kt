package com.steeplesoft.giftbook.ui.recipients

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewRecipientComponent(
    componentContext: ComponentContext,
    val recipientId: Long
) : ComponentContext by componentContext, KoinComponent {
    private val ideaDao : GiftIdeaDao by inject()
    private val recipientDao : RecipientDao by inject()
    private val nav : StackNavigation<NavigationConfig> by inject()

    var requestStatus = MutableValue(Status.LOADING)
    var ideas : List<GiftIdea> = emptyList()
    lateinit var recipient: Recipient

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                recipient = recipientDao.getRecipient(recipientId)
                loadIdeasForRecipient()
            }
        }
    }

    private suspend fun loadIdeasForRecipient() {
            ideas = ideaDao.getCurrentGiftIdeasForRecip(recipient.id)
            requestStatus.update { Status.SUCCESS }
    }

    fun addIdea() {
        nav.pushToFront(NavigationConfig.AddEditIdea(recipient))
    }

    fun editIdea(idea: GiftIdea) {
        nav.pushToFront(NavigationConfig.AddEditIdea(recipient, idea))
    }

    fun deleteIdea(idea: GiftIdea) {
        CoroutineScope(Dispatchers.IO).launch {
            ideaDao.delete(idea)
            loadIdeasForRecipient()
        }
    }

    fun editRecipient() {
        nav.pushToFront(NavigationConfig.AddEditRecipient(recipient))
    }

    fun deleteRecipient() {
        CoroutineScope(Dispatchers.Main).launch {
            recipientDao.delete(recipient)
            nav.pop()
        }
    }
}
