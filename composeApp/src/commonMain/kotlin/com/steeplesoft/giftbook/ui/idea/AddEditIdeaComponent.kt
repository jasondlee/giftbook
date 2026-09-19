package com.steeplesoft.giftbook.ui.idea

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.form.IdeaForm
import com.steeplesoft.giftbook.form.parseNonNegativeInt
import com.steeplesoft.giftbook.ui.componentScope
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
    private val scope = componentContext.componentScope()

    val form = IdeaForm(idea)

    fun save() {
        scope.launch {
            form.validate()
            if (form.isValid) {
                val title = form.title.state.value?.trim().orEmpty()
                val estimatedCost = parseNonNegativeInt(form.estimatedCost.state.value.orEmpty())
                if (title.isEmpty() || estimatedCost == null) return@launch

                val newIdea = GiftIdea(
                    id = idea?.id ?: 0,
                    title = title,
                    notes = form.notes.state.value ?: "",
                    recipientId = recipient.id,
                    estimatedCost = estimatedCost,
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
        scope.launch {
            nav.pop()
        }
    }
}
