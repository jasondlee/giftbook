package com.steeplesoft.giftbook.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.ComboBox
import com.steeplesoft.giftbook.ui.LoadingScreen
import com.steeplesoft.giftbook.ui.Status

@Composable
fun homeContent(component: HomeComponent,
                modifier: Modifier = Modifier) {
    val status by component.requestStatus.subscribeAsState()

    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (status) {
            Status.LOADING -> {
                LoadingScreen(modifier)
            }

            Status.SUCCESS -> {
                val optionItems = component.occasions //.map { OptionItems(it.name, it) }
                val selected: Occasion?  by remember { mutableStateOf(null) }

                ComboBox(label = "Current Occasion",
                    selected = selected,
                    onChange = { newValue  -> println("oc = $newValue") },
                    items = optionItems,
                    itemLabel = { item -> item?.name ?: "--" })
            }

            Status.ERROR -> {
                Text("Error loading meals")
            }
        }
    }
}
