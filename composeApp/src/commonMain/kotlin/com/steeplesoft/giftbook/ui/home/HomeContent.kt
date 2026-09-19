package com.steeplesoft.giftbook.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.theme.Typography
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.camper.components.ComboBox
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.ui.general.OccasionProgressRow
import org.koin.compose.koinInject

@Composable
fun Home(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val occasionProgress by component.occasionProgress.subscribeAsState()
    val current by component.occasion.subscribeAsState()
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    AsyncLoad(status) {
        val occasions by component.occasions.subscribeAsState()

        Column {
            ComboBox(
                label = "Current Occasion",
                selected = current,
                onChange = { newValue ->
                    component.onOccasionChange(newValue!!)
                },
                items = occasions,
                itemLabel = { item -> item?.name ?: "--" }
            )

            LazyColumn(
                modifier = Modifier.testTag("recipientList")
            ) {
                items(occasionProgress, key = { "${it.occasionId}:${it.recipient.id}" }) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Spacing.listItemBottomPadding)
                    ) {
                        Column(
                            modifier = Modifier.padding(Spacing.cardPadding)
                                .clickable {
                                    nav.bringToFront(NavigationConfig.ViewOccasionRecipient(it.recipient.id, it.occasionId))
                                }
                        ) {
                            Text(it.recipient.name, fontSize = Typography.secondaryTextSize)
                            OccasionProgressRow("Number", it.targetCount, it.actualCount)
                            OccasionProgressRow("Cost", it.targetCost, it.actualCost)
                        }
                    }
                }
            }
        }

    }
}
