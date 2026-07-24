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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.theme.Typography
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.giftbook.model.Recipient
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.giftbook.ui.general.DeleteConfirmationDialog
import com.steeplesoft.giftbook.ui.general.DividingLine
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

    DeleteConfirmationDialog(
        showDialog = deleteOccasionDialog,
        itemName = occasion.name,
        onConfirm = { component.delete() }
    )
    
    DeleteConfirmationDialog(
        showDialog = deleteRecipientDialog,
        itemName = "${recip.value?.name} from ${occasion.name}",
        onConfirm = { component.deleteRecip(recip.value!!) }
    )
    AsyncLoad(status) {
        ActionButton(
            onClick = {
                component.addRecipient()
            }
        )
        LazyColumn(modifier = modifier) {
            item {
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
                    fontSize = Typography.primaryTextSize,
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Date: ")
                        }
                        append(occasion.eventDate.format(LocalDate.Formats.ISO))
                    },
                    fontSize = Typography.primaryTextSize,
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Event Type: ")
                        }
                        append(occasion.eventType.label)
                    },
                    fontSize = Typography.primaryTextSize,
                )
                Text(
                    "Recipients:",
                    modifier = Modifier.padding(top = Spacing.internalPadding),
                    fontWeight = FontWeight.Bold,
                    fontSize = Typography.primaryTextSize
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
                            fontSize = Typography.primaryTextSize
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
