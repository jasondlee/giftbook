package com.steeplesoft.giftbook.ui.occasionRecip

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.form.OccasionRecipForm
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddEditOccasionRecipientComponent(
    val componentContext: ComponentContext,
    val occasion: Occasion,
    var recipient: Recipient? = null,
    var occasionRecipient: OccasionRecipient? = null
) : ComponentContext by componentContext, KoinComponent {
    private val nav: StackNavigation<NavigationConfig> by inject()
    private val occasionDao: OccasionDao by inject()
    private val recipientDao: RecipientDao by inject()

    var form = OccasionRecipForm(occasionRecipient)
    var requestStatus: MutableValue<Status> = MutableValue(Status.LOADING)
    var recipients: MutableValue<List<Recipient>> = MutableValue(emptyList())

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                if (recipient == null) {
                    val allRecips = recipientDao.getAll()
                    val recipsForOccasion = recipientDao.getRecipientListForOccasion(occasion.id).map { it.id }
                    val available = allRecips.filter { !recipsForOccasion.contains(it.id) }
                    recipients.update { available }
                }

                if (recipient != null && occasionRecipient == null) {
                    occasionRecipient = recipientDao.getRecipientForOccasion(occasion.id, recipient!!.id)
                }

                form = OccasionRecipForm(occasionRecipient)

                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun save() {
        CoroutineScope(Dispatchers.Main).launch {
            if (recipient != null) {
                val or = OccasionRecipient(
                    occasionId = occasion.id,
                    recipientId = recipient!!.id,
                    targetCost = form.cost.state.value ?: 0,
                    targetCount = form.count.state.value ?: 0
                )

                if (occasionRecipient != null) {
                    occasionDao.updateOccasionRecip(or)
                } else {
                    occasionDao.insertOccasionRecip(or)
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
