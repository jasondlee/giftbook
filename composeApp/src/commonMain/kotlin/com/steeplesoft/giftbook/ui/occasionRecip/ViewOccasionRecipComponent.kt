package com.steeplesoft.giftbook.ui.occasionRecip

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.general.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface ViewOccasionRecipient {
    val occasionRecip: OccasionRecipient
    var recip : Recipient
    var occasion : Occasion
    var requestStatus: MutableValue<Status>

    fun edit()
    fun delete()
}

class DefaultViewOccasionRecipient(
    componentContext: ComponentContext,
    private val recipId: Int,
    private val occasionId: Int
) : ViewOccasionRecipient,
    ComponentContext by componentContext,
    KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()

    override lateinit var occasionRecip: OccasionRecipient
    override lateinit var recip: Recipient
    override lateinit var occasion: Occasion
    override var requestStatus: MutableValue<Status> = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                occasionRecip = db.recipientDao().getRecipientForOccasion(occasionId, recipId)
                recip = db.recipientDao().getRecipient(recipId)
                occasion = db.occasionDao().getOccasion(occasionId)
                requestStatus.update { Status.SUCCESS }
            }
        }
    }


    override fun edit() {
//        nav.pushToFront(NavigationConfig.AddEditOccasion(occasion))
    }

    override fun delete() {
        CoroutineScope(Dispatchers.Main).launch {
//            db.occasionDao().delete(occasion)

            nav.pop()
        }
    }
}
