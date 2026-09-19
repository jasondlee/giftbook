package com.steeplesoft.giftbook.ui.recipients

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.form.RecipientForm
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.componentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddEditRecipientComponent(
    val componentContext: ComponentContext,
    var recipient: Recipient? = null
) : ComponentContext by componentContext, KoinComponent {
    private val nav: StackNavigation<NavigationConfig> by inject()
    private val recipientDao: RecipientDao by inject()
    private val scope = componentContext.componentScope()
    var form = RecipientForm(recipient)

    fun save() {
        scope.launch {
            form.validate()
            val name = form.name.state.value?.trim().orEmpty()
            if (!form.isValid || name.isEmpty()) return@launch

            val recip = Recipient(
                id = recipient?.id ?: 0,
                name = name
            )

            if (recipient == null) {
                recipientDao.insertAll(recip)
            } else {
                recipientDao.update(recip)
            }

            nav.pop()
        }
    }

    fun cancel() {
        scope.launch {
            nav.pop()
        }
    }
}
