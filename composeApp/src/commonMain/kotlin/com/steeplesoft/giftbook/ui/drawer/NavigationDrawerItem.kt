package com.steeplesoft.mobile.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationDrawerItem(
    val image: ImageVector,
    val label: String,
    val text: String = label
)
