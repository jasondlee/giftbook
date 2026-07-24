package com.steeplesoft.giftbook.ui.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import com.steeplesoft.camper.components.ConfirmationDialog

@Composable
fun DeleteConfirmationDialog(
    showDialog: MutableState<Boolean>,
    itemName: String,
    onConfirm: () -> Unit,
    dialogTitle: String = "Confirmation",
    icon: ImageVector = Icons.Filled.QuestionMark
) {
    if (showDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                showDialog.value = false
                onConfirm()
            },
            dialogTitle = dialogTitle,
            dialogText = "Are you sure you want to delete $itemName?",
            icon = icon
        )
    }
}
