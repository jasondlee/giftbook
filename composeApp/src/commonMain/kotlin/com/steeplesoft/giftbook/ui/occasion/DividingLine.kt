package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividingLine() {
    HorizontalDivider(
        modifier = Modifier.Companion.padding(horizontal = 5.dp, vertical = 5.dp),
        thickness = 2.dp
    )
}
