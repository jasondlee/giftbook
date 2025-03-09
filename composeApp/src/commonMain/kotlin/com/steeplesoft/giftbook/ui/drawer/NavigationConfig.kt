package com.steeplesoft.giftbook.ui.drawer

import com.steeplesoft.giftbook.database.model.Occasion
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationConfig {
    @Serializable
    data object Home : NavigationConfig

    @Serializable
    data object Occasions : NavigationConfig

    @Serializable
    data class ViewOccasion(val occasion: Occasion) : NavigationConfig

    @Serializable
    data class AddEditOccasion(val occasion: Occasion? = null) : NavigationConfig

    @Serializable
    data class ViewOccasionRecip(val recipId: Int, val occasionId: Int) : NavigationConfig
}
