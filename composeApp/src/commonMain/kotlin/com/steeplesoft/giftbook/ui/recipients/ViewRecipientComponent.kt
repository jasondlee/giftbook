package com.steeplesoft.giftbook.ui.recipients

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
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.componentScope
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

    val requestStatus = MutableValue(Status.LOADING)
    val ideas = MutableValue<List<GiftIdea>>(emptyList())
    val recipient = MutableValue(RecipientState())
    private val scope = componentContext.componentScope()

    init {
        componentContext.doOnResume {
            scope.launch(Dispatchers.IO) {
                val loadedRecipient = recipientDao.getRecipient(recipientId)
                recipient.update { RecipientState(loadedRecipient) }
                loadIdeasForRecipient(loadedRecipient)
            }
        }
    }

    private suspend fun loadIdeasForRecipient(loadedRecipient: Recipient = requireNotNull(recipient.value.value)) {
        val loadedIdeas = ideaDao.getCurrentGiftIdeasForRecip(loadedRecipient.id)
        ideas.update { loadedIdeas }
        requestStatus.update { Status.SUCCESS }
    }

    fun addIdea() {
        recipient.value.value?.let { nav.pushToFront(NavigationConfig.AddEditIdea(it)) }
    }

    fun editIdea(idea: GiftIdea) {
        recipient.value.value?.let { nav.pushToFront(NavigationConfig.AddEditIdea(it, idea)) }
    }

    fun deleteIdea(idea: GiftIdea) {
        scope.launch(Dispatchers.IO) {
            ideaDao.delete(idea)
            val loadedRecipient = recipient.value.value ?: return@launch
            loadIdeasForRecipient(loadedRecipient)
        }
    }

    fun editRecipient() {
        recipient.value.value?.let { nav.pushToFront(NavigationConfig.AddEditRecipient(it)) }
    }

    fun deleteRecipient() {
        scope.launch {
            recipient.value.value?.let { recipientDao.delete(it) }
            nav.pop()
        }
    }
}

data class RecipientState(val value: Recipient? = null)
