package com.steeplesoft.giftbook.ui.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionButton(
    icon: ImageVector = Icons.Filled.Add,
    contentDescription: String = "Add",
    onClick: () -> Unit
) {
    FloatingActionButton(onClick = onClick) {
        Icon(icon, contentDescription = contentDescription)
    }
}
