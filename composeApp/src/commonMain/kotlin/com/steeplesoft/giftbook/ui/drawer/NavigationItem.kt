package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.steeplesoft.giftbook.NavigationConfig

sealed class NavigationItem(var route: NavigationConfig, var image: ImageVector, var title: String) {
    data object Home : NavigationItem(NavigationConfig.Home(), Icons.Rounded.Home, "Home")
    data object Occasions : NavigationItem(NavigationConfig.Occasions, Icons.Rounded.CalendarToday, "Occasions")
    data object Recipients : NavigationItem(NavigationConfig.Recipients, Icons.Rounded.People, "Recipients")
//    data object GiftIdeas : NavigationItem(NavigationConfig.Home(), Icons.Rounded.Lightbulb, "Ideas")
}
