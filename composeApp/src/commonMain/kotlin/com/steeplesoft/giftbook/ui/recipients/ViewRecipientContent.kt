package com.steeplesoft.giftbook.ui.recipients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.giftbook.ui.general.DividingLine
import com.steeplesoft.kmpform.components.ConfirmationDialog
import com.steeplesoft.kmpform.components.asyncLoad
import org.koin.compose.koinInject

@Composable
fun ViewRecipient(
    component: ViewRecipientComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    val deleteRecipientDialog = remember { mutableStateOf(false) }
    val deleteIdeaDialog = remember { mutableStateOf(false) }
    var toDelete: MutableState<GiftIdea?> = remember { mutableStateOf(null) }

    if (deleteRecipientDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { deleteRecipientDialog.value = false },
            onConfirmation = {
                deleteRecipientDialog.value = false
                component.deleteRecipient()
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure you want to delete ${component.recipient.name}?",
            icon = Icons.Filled.QuestionMark
        )
    }
    if (deleteIdeaDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { deleteIdeaDialog.value = false },
            onConfirmation = {
                deleteIdeaDialog.value = false
                component.deleteIdea(toDelete.value!!)
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure you want to delete ${toDelete.value?.title}?",
            icon = Icons.Filled.QuestionMark
        )
    }

    asyncLoad(status) {
        ActionButton(
            onClick = {
                component.addIdea()
            }
        )
        Column(modifier = modifier) {
            AddEditHeader(
                label = component.recipient.name,
                editClick = { component.editRecipient() },
                deleteClick = { deleteRecipientDialog.value = true }
            )
            LazyColumn {
                items(component.ideas) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                    component.editIdea(item)
                            }) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row {
                                Text(
                                    text = item.title,
                                    fontSize = 24.sp
                                )
                            }
                            Row {
                                if (!item.notes.isNullOrBlank()) {
                                    Text(
                                        text = item.notes!!,
                                        fontStyle = FontStyle.Italic,
                                    )
                                }
                            }
                        }
                        Column {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Edit",
                                tint = Color.Red,
                                modifier = Modifier.clickable {
                                    toDelete.value = item
                                    deleteIdeaDialog.value = true
                                })

                        }
                    }
                    DividingLine()
                }
            }
        }
    }
}
