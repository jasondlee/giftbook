package com.steeplesoft.giftbook.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.camper.components.ComboBox
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.ui.general.ActionButton
import org.koin.compose.koinInject

@Composable
fun Home(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val occasionProgress by component.occasionProgress.subscribeAsState()
    val nav : StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncLoad(status) {
            val occasions by component.occasions.subscribeAsState()
            val current: Occasion? by remember { mutableStateOf(component.occasion) }

            ComboBox(label = "Current Occasion",
                selected = current,
                onChange = { newValue ->
                    component.onOccasionChange(newValue!!)
                },
                items = occasions,
                itemLabel = { item -> item?.name ?: "--" }
            )

            LazyColumn {
                items(occasionProgress) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp)
                                .clickable {
                                    nav.bringToFront(NavigationConfig.ViewOccasionRecipient(it.recipient.id, it.occasionId))
                                }
                        ) {
                            Text(it.recipient.name, fontSize = 18.sp)
                            OccasionProgressRow("Number", it.targetCount, it.actualCount)
                            OccasionProgressRow("Cost", it.targetCost, it.actualCost)
                        }
                    }
                }
            }

            ActionButton(
                onClick = {
                    component.addRecipient()
                }
            )
        }

    }
}

@Composable
fun OccasionProgressRow(
    label: String,
    targetNumber: Int,
    actualNumber: Int
) {
    Row {
        Column(Modifier.weight(0.2f)) {
            Text(label)
        }
        Column(modifier = Modifier.align(Alignment.CenterVertically).weight(0.8f)) {
            LinearProgressIndicator(
                progress = { (actualNumber.toFloat() / targetNumber) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
