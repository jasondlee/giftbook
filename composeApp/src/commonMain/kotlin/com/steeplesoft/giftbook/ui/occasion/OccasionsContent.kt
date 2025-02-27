package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.clickable
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
import com.arkivanov.decompose.router.stack.pushToFront
import com.steeplesoft.giftbook.ui.asyncLoad
import com.steeplesoft.giftbook.ui.root.NavigationConfig
import com.steeplesoft.giftbook.ui.root.nav

@Composable
fun occasionList(
    component: OccasionsComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        asyncLoad(status) {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    Text("Occasions", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            nav.pushToFront(NavigationConfig.AddEditOccasion())
                        },
//                        containerColor = secondaryColor,
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
                                .clickable {
                                    nav.pushToFront(NavigationConfig.ViewOccasion(occasion))
                                }
                        ) {
                            Text(text = "${occasion.name} - ${occasion.eventDate}")
                        }
                    }
                }
            }
        }
/*
        when (status) {
            Status.LOADING -> {
                LoadingScreen(modifier)
            }

            Status.SUCCESS -> {

            }

            Status.ERROR -> {
                Text("Error loading meals")
            }
        }
*/
    }
}
