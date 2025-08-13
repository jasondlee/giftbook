package com.steeplesoft.giftbook.ui.drawer

import com.steeplesoft.giftbook.NavigationConfig
import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.calendar
import giftbook.composeapp.generated.resources.group
import giftbook.composeapp.generated.resources.home
import org.jetbrains.compose.resources.DrawableResource

sealed class NavigationItem(var route: NavigationConfig, var image: DrawableResource, var title: String) {
    data object Home : NavigationItem(NavigationConfig.Home(), Res.drawable.home, "Home")
    data object Occasions : NavigationItem(NavigationConfig.Occasions, Res.drawable.calendar, "Occasions")
    data object Recipients : NavigationItem(NavigationConfig.Recipients, Res.drawable.group, "Recipients")
//    data object GiftIdeas : NavigationItem(NavigationConfig.Home(), Icons.Rounded.Lightbulb, "Ideas")
}
