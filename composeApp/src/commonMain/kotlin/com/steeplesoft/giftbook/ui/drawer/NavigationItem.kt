package com.steeplesoft.mobile.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var imageVector: ImageVector, var title: String) {
    data object Back : NavigationItem("back", Icons.AutoMirrored.Rounded.ArrowBack, "Back")
    data object Home : NavigationItem("home", Icons.Rounded.Home, "Home")
}
