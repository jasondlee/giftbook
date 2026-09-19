package com.steeplesoft.giftbook.ui.occasionRecip

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.form.OccasionRecipForm
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.componentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddEditOccasionRecipientComponent(
    val componentContext: ComponentContext,
    val occasion: Occasion,
    recipient: Recipient? = null,
    occasionRecipient: OccasionRecipient? = null
) : ComponentContext by componentContext, KoinComponent {
    private val nav: StackNavigation<NavigationConfig> by inject()
    private val occasionDao: OccasionDao by inject()
    private val recipientDao: RecipientDao by inject()

    val recipient = MutableValue(recipient)
    val occasionRecipient = MutableValue(occasionRecipient)
    var form = OccasionRecipForm(occasionRecipient.value)
    val requestStatus: MutableValue<Status> = MutableValue(Status.LOADING)
    val recipients: MutableValue<List<Recipient>> = MutableValue(emptyList())
    private val scope = componentContext.componentScope()

    init {
        componentContext.doOnResume {
            scope.launch(Dispatchers.IO) {
                if (recipient.value == null) {
                    val allRecips = recipientDao.getAll()
                    val recipsForOccasion = recipientDao.getRecipientListForOccasion(occasion.id).map { it.id }
                    val available = allRecips.filter { !recipsForOccasion.contains(it.id) }
                    recipients.update { available }
                }

                if (recipient.value != null && occasionRecipient.value == null) {
                    occasionRecipient.update { recipientDao.getRecipientForOccasion(occasion.id, recipient.value!!.id) }
                }

                form = OccasionRecipForm(occasionRecipient.value)

                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun save() {
        scope.launch {
            form.validate()
            if (form.isValid) {
                recipient.value?.let { recip ->
                    val or = OccasionRecipient(
                        occasionId = occasion.id,
                        recipientId = recip.id,
                        targetCost = form.cost.state.value ?: 0,
                        targetCount = form.count.state.value ?: 0
                    )

                    if (occasionRecipient.value != null) {
                        occasionDao.updateOccasionRecip(or)
                    } else {
                        occasionDao.insertOccasionRecip(or)
                    }

                    nav.pop()
                }
            }
        }
    }

    fun cancel() {
        scope.launch {
            nav.pop()
        }
    }
}
