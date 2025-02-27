@file:OptIn(ExperimentalMaterial3Api::class)

package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.steeplesoft.giftbook.mainColor
import com.steeplesoft.giftbook.secondaryColor
import com.steeplesoft.giftbook.ui.ConfirmationDialog

@Composable
fun occasionView(
    component: OccasionComponent,
    modifier: Modifier = Modifier
) {
    val occasion = component.occasion
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Occasion Details",
                    fontWeight = FontWeight.Bold, fontSize = 30.sp) },
                actions = {
                    IconButton(
                        colors = IconButtonColors(
                            containerColor = secondaryColor,
                            contentColor = Color.Black,
                            disabledContainerColor = mainColor,
                            disabledContentColor = Color.Black
                        ),
                        onClick = {
                            component.edit()
                        }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    IconButton(
                        colors = IconButtonColors(
                            containerColor = secondaryColor,
                            contentColor = Color.Black,
                            disabledContainerColor = mainColor,
                            disabledContentColor = Color.Black
                        ),
                        onClick = {
                            showDialog.value = true
                        }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { paddingValues ->
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
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            item {
                Column {
                    Text(text = "${occasion.name} - ${occasion.eventDate}")
                }
            }
        }
    }
}
