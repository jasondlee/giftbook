package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.steeplesoft.giftbook.ui.root.NavigationConfig

sealed class NavigationItem(var route: NavigationConfig, var imageVector: ImageVector, var title: String) {
    data object Home : NavigationItem(NavigationConfig.Home, Icons.Rounded.Home, "Home")
    data object Occasions : NavigationItem(NavigationConfig.Occasions, Icons.Rounded.CalendarToday, "Occasions")
    data object Recipients : NavigationItem(NavigationConfig.Home, Icons.Rounded.Person, "Recipients")
    data object GiftIdeas : NavigationItem(NavigationConfig.Home, Icons.Rounded.Lightbulb, "Ideas")
}
