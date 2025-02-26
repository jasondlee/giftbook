package com.steeplesoft.giftbook.ui.root

import com.arkivanov.decompose.router.stack.StackNavigation
import kotlinx.serialization.Serializable

val nav = StackNavigation<NavigationConfig>()

@Serializable
sealed interface NavigationConfig {
    @Serializable
    data object ClickMe : NavigationConfig

    @Serializable
    data object Home : NavigationConfig

    @Serializable
    data object Occasions : NavigationConfig
}
