package com.steeplesoft.giftbook.ui.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun OccasionProgressRow(
    label: String,
    targetNumber: Int,
    actualNumber: Int
) {
    Row {
        Column(Modifier.Companion.weight(0.2f)) {
            Text(label)
        }
        Column(modifier = Modifier.Companion.align(Alignment.Companion.CenterVertically).weight(0.8f)) {
            LinearProgressIndicator(
                progress = { (actualNumber.toFloat() / targetNumber) },
                modifier = Modifier.Companion.fillMaxWidth(),
            )
        }
    }
}
