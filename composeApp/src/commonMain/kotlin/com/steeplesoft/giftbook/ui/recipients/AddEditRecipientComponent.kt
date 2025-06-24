package com.steeplesoft.giftbook.ui.recipients

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
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
    var form = RecipientForm(recipient)

    init {
//        componentContext.doOnResume {
//            CoroutineScope(Dispatchers.IO).launch {
//
//            }
//        }
    }

    fun save() {
        CoroutineScope(Dispatchers.Main).launch {
            val recip = Recipient(
                id = recipient?.id ?: 0,
                name = form.name.state.value!!
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
        CoroutineScope(Dispatchers.Main).launch {
            nav.pop()
        }
    }
}
