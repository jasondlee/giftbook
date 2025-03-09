package com.steeplesoft.giftbook.ui.general

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FAB(
    icon: ImageVector = Icons.Filled.Add,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp),
    ) {
        Row(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(
                onClick = onClick,
            ) {
                Icon(icon, contentDescription = "Floating action button")
            }
        }

    }
}
