package com.steeplesoft.giftbook.ui.occasionRecip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog

@ExperimentalMaterial3Api
@Composable
fun GiftCostDialog(
    onSaveRequest: (Int) -> Unit,
    onCancelRequest: () -> Unit
) {
    Dialog(onDismissRequest = onCancelRequest ) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            var cost by remember { mutableStateOf("") }
            var textFieldSize by remember { mutableStateOf(Size.Zero) }

            Column(modifier = Modifier.fillMaxWidth()
                .padding(10.dp)) {
                OutlinedTextField(
                    value = cost,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            textFieldSize = coordinates.size.toSize()
                        },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Clear, "contentDescription",
                            Modifier.clickable { cost = "" })
                    },
                    label = { Text("Item Cost") },
                    onValueChange = {
                        cost = it
                    }
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(modifier = Modifier.weight(0.45f),
                        onClick = {
                            onSaveRequest(if (cost.isNotBlank()) cost.toInt() else 0)
                        }
                    ) {
                        Text("OK")
                    }
                    TextButton(
                        modifier = Modifier.weight(0.45f),
                        onClick = onCancelRequest
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
