package com.steeplesoft.giftbook.ui.occasionRecip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.giftbook.ui.general.ConfirmationDialog
import com.steeplesoft.giftbook.ui.general.FAB
import com.steeplesoft.giftbook.ui.general.asyncLoad

@Composable
fun viewOccasionRecip(
    component: ViewOccasionRecipient,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val status by component.requestStatus.subscribeAsState()

    if (showDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                showDialog.value = false
                component.delete()
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure you want to remove ${component.recip.name} from ${component.occasion.name}?"
        )
    }
    asyncLoad(status) {
        FAB(
            onClick = {
                //
            }
        )
        Column(modifier = Modifier.padding(10.dp)) {
            AddEditHeader(
                label = "Occasion Recipient Details",
                editClick = { component.edit() },
                deleteClick = { showDialog.value = true }
            )
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Occasion: ")
                }
                append(component.occasion.name)
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Recipient: ")
                }
                append(component.recip.name)
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Target Gift Count: ")
                }
                append(component.occasionRecip.targetCount.toString())
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Target Gift Cost: ")
                }
                append(component.occasionRecip.targetCost.toString())
            })
        }
    }
}
