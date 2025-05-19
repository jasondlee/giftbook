package com.steeplesoft.giftbook.ui.recipients

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewRecipientComponent(
    componentContext: ComponentContext,
    val recipient: Recipient
) : ComponentContext by componentContext, KoinComponent {
    private val ideaDao : GiftIdeaDao by inject()
    private val nav : StackNavigation<NavigationConfig> by inject()

    var requestStatus = MutableValue(Status.LOADING)
    var ideas : List<GiftIdea> = emptyList()

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                ideas = ideaDao.getCurrentGiftIdeasForRecip(recipient.id)
                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun edit() {
//        nav.pushToFront(NavigationConfig.AddEditOccasion(occasion))
    }

    fun delete() {
        CoroutineScope(Dispatchers.Main).launch {
//            occasionDao.delete(occasion)

            nav.pop()
        }
    }
}
