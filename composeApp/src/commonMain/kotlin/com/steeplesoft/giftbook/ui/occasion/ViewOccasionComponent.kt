package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionWithRecipients
import com.steeplesoft.giftbook.ui.Status
import com.steeplesoft.giftbook.ui.root.NavigationConfig
import com.steeplesoft.giftbook.ui.root.nav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

interface ViewOccasionComponent {
    val occasion: Occasion
    var requestStatus: MutableValue<Status>
    var recips: OccasionWithRecipients?
    fun edit()
    fun delete()
}

class DefaultViewOccasionComponent(
    componentContext: ComponentContext,
    override val occasion: Occasion
) : ViewOccasionComponent,
    ComponentContext by componentContext {
    override var recips : OccasionWithRecipients? = null
    override var requestStatus = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                recips = db.occasionDao().getOccasionRecipients(occasion.id)

                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    override fun edit() {
        nav.pushToFront(NavigationConfig.AddEditOccasion(occasion))
    }

    override fun delete() {
        CoroutineScope(Dispatchers.Main).launch {
            db.occasionDao().delete(occasion)

            nav.pop()
        }
    }
}
