@file:OptIn(ExperimentalMaterial3Api::class)

package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.ui.ConfirmationDialog
import com.steeplesoft.giftbook.ui.asyncLoad
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@Composable
fun occasionView(
    component: OccasionComponent,
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
            dialogText = "Are you sure you want to delete ${occasion.name}?",
            icon = Icons.Default.QuestionMark
        )
    }
    asyncLoad(status) {
        FAB()
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            item {
                addEditHeader(
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
                items(recips.recipient) { recip ->
                    Text(text = recip.name)
                }
            }
        }
    }

}

@Composable
fun addEditHeader(
    label: String,
    editClick: () -> Unit,
    deleteClick: () -> Unit
) {
//    val colors = IconButtonColors(
//        containerColor = secondaryColor,
//        contentColor = Color.Black,
//        disabledContainerColor = mainColor,
//        disabledContentColor = Color.Black
//    )
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().weight(.5f)) {
            Text(label, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }
        Column {
            IconButton(/*colors = colors,*/ onClick = editClick) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
        }
        Column {
            IconButton(/*colors = colors,*/ onClick = deleteClick) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}


@Composable
fun FAB() {
    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp),
    ) {
        Row(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(
//                containerColor = secondaryColor,
                onClick = {
                    /*do something*/
                },
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Button")
            }
        }

    }
}
