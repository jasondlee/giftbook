package com.steeplesoft.giftbook.ui.drawer

import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.Recipient
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationConfig {
    @Serializable
    data class Home(val occasion: Occasion? = null) : NavigationConfig

    @Serializable
    data object Occasions : NavigationConfig

    @Serializable
    data class ViewOccasion(val occasion: Occasion) : NavigationConfig

    @Serializable
    data class AddEditOccasion(val occasion: Occasion? = null) : NavigationConfig

    @Serializable
    data class ViewOccasionRecipient(val recipId: Long, val occasionId: Long) : NavigationConfig

    @Serializable
    data class AddEditOccasionRecipient(val occasion: Occasion, val recipient: Recipient? = null, val occasionRecip: OccasionRecipient? = null): NavigationConfig

    @Serializable
    data object Recipients : NavigationConfig

    @Serializable
    data class AddEditRecipient(val recipient: Recipient? = null) : NavigationConfig

    @Serializable
    data class ViewRecipient(val recipient: Recipient) : NavigationConfig

    @Serializable
    data class AddEditIdea(val recipient: Recipient, val idea: GiftIdea? = null) : NavigationConfig
}
