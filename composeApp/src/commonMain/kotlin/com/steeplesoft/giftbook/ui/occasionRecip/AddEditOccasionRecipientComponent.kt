package com.steeplesoft.giftbook.ui.occasionRecip

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AddEditOccasionRecipientComponent {
    val occasion: Occasion
    val recipient: Recipient?
}

class DefaultAddEditOccasionRecipientComponent(
    val componentContext: ComponentContext,
    override val occasion: Occasion,
    override val recipient: Recipient?
) : AddEditOccasionRecipientComponent,
    ComponentContext by componentContext,
    KoinComponent {
    private val nav: StackNavigation<NavigationConfig> by inject()
    private val occasionDao: OccasionDao by inject()
    private val recipientDao: RecipientDao by inject()

}
