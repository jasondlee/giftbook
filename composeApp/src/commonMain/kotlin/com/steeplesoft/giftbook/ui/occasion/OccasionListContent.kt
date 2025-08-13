package com.steeplesoft.giftbook.ui.occasion

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.general.DividingLine
import com.steeplesoft.kmpform.components.asyncLoad
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun OccasionList(
    component: OccasionListComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val occasions by component.occasions.subscribeAsState()

    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    Column(modifier = modifier) {
        asyncLoad(status) {
            LazyColumn {
                item {
                    Text("Gift Giving Occasions", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                }
                items(occasions) { occasion ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                nav.bringToFront(NavigationConfig.ViewOccasion(occasion.id))
                            }) {
                        Image(
                            modifier = Modifier.size(48.dp)
                                .padding(end = 10.dp),
                            painter = painterResource(occasion.eventType.image),
                            contentDescription = ""
                        )
                        Column {
                            Text(
                                fontSize = 24.sp,
                                text = occasion.name
                            )
                            Text(
                                fontSize = 18.sp,
                                text = "${occasion.eventDate}"
                            )
                        }
                    }
                    DividingLine()
                }
            }
            ActionButton(
                onClick = { nav.bringToFront(NavigationConfig.AddEditOccasion()) }
            )
        }
    }
}
