package com.steeplesoft.giftbook.ui.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AddEditHeader(
    label: String,
    editClick: () -> Unit,
    deleteClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().weight(.5f)) {
            Text(label, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }
        Column {
            IconButton(onClick = editClick) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
        }
        Column {
            IconButton(onClick = deleteClick) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}
