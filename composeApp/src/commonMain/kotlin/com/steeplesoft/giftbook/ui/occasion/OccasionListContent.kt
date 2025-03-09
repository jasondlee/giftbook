package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.general.FAB
import com.steeplesoft.giftbook.ui.general.asyncLoad
import org.koin.compose.koinInject

@Composable
fun occasionList(
    component: OccasionListComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val nav : StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    Column(
        modifier = modifier.padding(10.dp),
    ) {
        asyncLoad(status) {
            Text("Occasions", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            FAB(
                onClick = { nav.bringToFront(NavigationConfig.AddEditOccasion()) }
            )
            LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                items(component.occasions) { occasion ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clickable {
                                nav.bringToFront(NavigationConfig.ViewOccasion(occasion))
                            }
                    ) {
                        Text(text = "${occasion.name} - ${occasion.eventDate}")
                    }
                }
            }
        }
    }
}
