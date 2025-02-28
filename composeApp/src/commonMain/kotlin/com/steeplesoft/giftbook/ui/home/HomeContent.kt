package com.steeplesoft.giftbook.ui.home

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.ComboBox
import com.steeplesoft.giftbook.ui.FAB
import com.steeplesoft.giftbook.ui.asyncLoad

@Composable
fun homeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val occasionProgress by component.occasionProgress.subscribeAsState()

    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        asyncLoad(status) {
            val optionItems = component.occasions
            var current: Occasion? by remember { mutableStateOf(null) }

            ComboBox(label = "Current Occasion",
                selected = current,
                onChange = { newValue ->
                    newValue?.let {
                        component.onOccasionChange(newValue)
                    }
                },
                items = optionItems,
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
                        ) {
                            Text(it.recipient.name, fontSize = 18.sp)
                            OccasionProgressRow("Number", it.targetCount.toFloat(), it.actualCount.toFloat())
                            OccasionProgressRow("Cost", it.targetCost, it.actualCost)
                        }
                    }
                }
            }
            FAB(
                onClick = {

                }
            )
        }

    }
}

@Composable
fun OccasionProgressRow(
    label: String,
    targetNumber: Float,
    actualNumber: Float
) {
    Row {
        Column(Modifier.weight(0.2f)) {
            Text(label)
        }
        Column(modifier = Modifier.align(Alignment.CenterVertically).weight(0.8f)) {
            LinearProgressIndicator(
                progress = { actualNumber / targetNumber },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
