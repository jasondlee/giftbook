package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationDrawerItem(
    val image: ImageVector,
    val label: String,
    val text: String = label
)
