package com.steeplesoft.giftbook.ui.general

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SaveCancelButtons(
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    saveText: String = "Save",
    cancelText: String = "Cancel"
) {
    Row(modifier = modifier.padding(top = 5.dp).fillMaxWidth()) {
        Button(
            onClick = onSave,
            modifier = Modifier.padding(end = 3.dp)
                .fillMaxWidth(0.5f)
        ) {
            Text(saveText)
        }
        Button(
            onClick = onCancel,
            modifier = Modifier.padding(start = 3.dp)
                .fillMaxWidth()
        ) {
            Text(cancelText)
        }
    }
}
