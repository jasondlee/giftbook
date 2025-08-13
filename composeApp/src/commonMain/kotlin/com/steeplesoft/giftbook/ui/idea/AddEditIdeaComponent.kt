package com.steeplesoft.giftbook.ui.idea

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.form.IdeaForm
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.model.Recipient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddEditIdeaComponent(
    componentContext: ComponentContext,
    val recipient: Recipient,
    val idea: GiftIdea? = null
) : ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val ideaDao : GiftIdeaDao by inject()

    val form = IdeaForm(idea)

    fun save() {
        CoroutineScope(Dispatchers.Main).launch {
            form.validate()
            if (form.isValid) {
                val newIdea = GiftIdea(
                    idea?.id ?: 0,
                    form.title.state.value!!,
                    notes = form.notes.state.value ?: "",
                    recipientId = recipient.id,
                    estimatedCost = form.estimatedCost.state.value?.toInt() ?: 0,
                )
                if (idea == null) {
                    newIdea.id = ideaDao.insert(newIdea)
                } else {
                    ideaDao.update(newIdea)
                }
                nav.pop()
            }
        }
    }

    fun cancel() {
        CoroutineScope(Dispatchers.Main).launch {
            nav.pop()
        }
    }
}
