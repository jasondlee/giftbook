package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var imageVector: ImageVector, var title: String) {
    data object Back : NavigationItem("back", Icons.AutoMirrored.Rounded.ArrowBack, "Back")
    data object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
    data object Occasions : NavigationItem("occasion", Icons.Rounded.CalendarToday, "Occasions")
    data object Recipients : NavigationItem("recipients", Icons.Rounded.Person, "Recipients")
    data object GiftIdeas : NavigationItem("giftideas", Icons.Rounded.Lightbulb, "Ideas")
}
