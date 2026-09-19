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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.theme.Typography
import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.giftbook.ui.general.DeleteConfirmationDialog
import com.steeplesoft.giftbook.ui.general.DividingLine
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
    val ideas by component.ideas.subscribeAsState()

    AsyncLoad(status) {
        DeleteConfirmationDialog(
            showDialog = deleteRecipientDialog,
            itemName = component.recipient.value?.name.orEmpty(),
            onConfirm = { component.deleteRecipient() }
        )

        DeleteConfirmationDialog(
            showDialog = deleteIdeaDialog,
            itemName = toDelete.value?.title.orEmpty(),
            onConfirm = { toDelete.value?.let(component::deleteIdea) }
        )

        ActionButton(
            onClick = {
                component.addIdea()
            }
        )
        Column(modifier = modifier) {
            AddEditHeader(
                label = component.recipient.value?.name.orEmpty(),
                editClick = { component.editRecipient() },
                deleteClick = { deleteRecipientDialog.value = true }
            )
            LazyColumn {
                items(ideas, key = { it.id }) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                    component.editIdea(item)
                            }) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row {
                                Text(
                                    text = item.title,
                                    fontSize = Typography.primaryTextSize
                                )
                            }
                            Row {
                                if (!item.notes.isNullOrBlank()) {
                                    Text(
                                    text = item.notes.orEmpty(),
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
