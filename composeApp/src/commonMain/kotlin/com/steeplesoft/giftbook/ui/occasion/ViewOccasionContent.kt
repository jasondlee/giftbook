@file:OptIn(ExperimentalMaterial3Api::class)

package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.kmpform.components.ConfirmationDialog
import com.steeplesoft.kmpform.components.asyncLoad
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@Composable
fun ViewOccasion(
    component: ViewOccasionComponent,
    modifier: Modifier = Modifier
) {
    val occasion = component.occasion
    var recip: MutableState<Recipient?> = remember { mutableStateOf(null) }
    val deleteOccasionDialog = remember { mutableStateOf(false) }
    val deleteRecipientDialog = remember { mutableStateOf(false) }
    val status by component.requestStatus.subscribeAsState()

    if (deleteOccasionDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { deleteOccasionDialog.value = false },
            onConfirmation = {
                deleteOccasionDialog.value = false
                component.delete()
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure you want to delete ${occasion.name}?",
            icon = Icons.Filled.QuestionMark
        )
    }
    if (deleteRecipientDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { deleteRecipientDialog.value = false },
            onConfirmation = {
                deleteRecipientDialog.value = false
                component.deleteRecip(recip.value!!)
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure you want to remove ${recip.value?.name} from  ${occasion.name}?",
            icon = Icons.Filled.QuestionMark
        )
    }
    asyncLoad(status) {
        ActionButton(
            onClick = {
                component.addRecipient()
            }
        )
        LazyColumn(modifier = modifier) {
            item {
                val fontSize = 24.sp

                AddEditHeader(
                    label = "Occasion Details",
                    editClick = { component.edit() },
                    deleteClick = { deleteOccasionDialog.value = true }
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Name: ")
                        }
                        append(occasion.name)
                    },
                    fontSize = fontSize,
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Date: ")
                        }
                        append(occasion.eventDate.format(LocalDate.Formats.ISO))
                    },
                    fontSize = fontSize,
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Event Type: ")
                        }
                        append(occasion.eventType.label)
                    },
                    fontSize = fontSize,
                )
                Text(
                    "Recipients:",
                    modifier = Modifier.padding(top = 5.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                )
            }
            items(component.recips) { curr ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            component.editOccasionRecipient(curr)
                        }) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = curr.name,
                            fontSize = 24.sp
                        )
                    }
                    Column {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Edit",
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                recip.value = curr
                                deleteRecipientDialog.value = true
                            })

                    }
                }
                DividingLine()
            }
        }
    }
}
