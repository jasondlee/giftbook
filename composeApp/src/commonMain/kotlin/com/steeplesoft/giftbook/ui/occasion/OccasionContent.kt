package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.secondaryColor
import com.steeplesoft.giftbook.ui.LoadingScreen
import com.steeplesoft.giftbook.ui.Status

@Composable
fun occasionList(
    component: OccasionComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        when (status) {
            Status.LOADING -> {
                LoadingScreen(modifier)
            }

            Status.SUCCESS -> {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text("Occasions", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
//                            nav.pushToFront(NavigationConfig.AddEditPerson())
                            },
                            containerColor = secondaryColor,
                            contentColor = Color.Black
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                ) { innerPadding ->
                    LazyColumn(
                        contentPadding = innerPadding,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        items(component.occasions) { occasion ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                            ) {
                                Text(text = "${occasion.name} - ${occasion.eventDate}")
                            }
                        }
                    }
                }
            }

            Status.ERROR -> {
                Text("Error loading meals")
            }
        }
    }
}
