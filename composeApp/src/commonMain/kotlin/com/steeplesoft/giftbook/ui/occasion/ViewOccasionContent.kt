@file:OptIn(ExperimentalMaterial3Api::class)

package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.giftbook.ui.general.ConfirmationDialog
import com.steeplesoft.giftbook.ui.general.FAB
import com.steeplesoft.giftbook.ui.general.asyncLoad
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@Composable
fun viewOccasion(
    component: ViewOccasionComponent,
    modifier: Modifier = Modifier
) {
    val occasion = component.occasion
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
            dialogText = "Are you sure you want to delete ${occasion.name}?"
        )
    }
    asyncLoad(status) {
        FAB(
            onClick = {
                //
            }
        )
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            item {
                AddEditHeader(
                    label = "Occasion Details",
                    editClick = { component.edit() },
                    deleteClick = { showDialog.value = true }
                )
            }
            item {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Name: ")
                    }
                    append(occasion.name)
                })
            }
            item {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Date: ")
                    }
                    append(occasion.eventDate.format(LocalDate.Formats.ISO))
                })
            }
            item {
                Text(
                    "Recipients:",
                    fontWeight = FontWeight.Bold, fontSize = 24.sp
                )
            }
            component.recips?.let { recips ->
                items(recips.recipients) { recip ->
                    Text(text = recip.name)
                }
            }
        }
    }
}


