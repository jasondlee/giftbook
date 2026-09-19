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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.theme.Typography
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.ui.general.DividingLine
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
        AsyncLoad(status) {
            LazyColumn {
                item {
                    Text("Gift Giving Occasions", fontWeight = FontWeight.Bold, fontSize = Typography.headerSize)
                }
                items(occasions, key = { it.id }) { occasion ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                nav.bringToFront(NavigationConfig.ViewOccasion(occasion.id))
                            }) {
                        Image(
                            modifier = Modifier.size(48.dp)
                                .padding(end = Spacing.screenPadding),
                            painter = painterResource(occasion.eventType.image),
                            contentDescription = occasion.eventType.label
                        )
                        Column {
                            Text(
                                fontSize = Typography.primaryTextSize,
                                text = occasion.name
                            )
                            Text(
                                fontSize = Typography.secondaryTextSize,
                                text = "${occasion.eventDate}"
                            )
                        }
                    }
                    DividingLine()
                }
            }
        }
    }
}
