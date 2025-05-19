package com.steeplesoft.giftbook.ui.recipients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.kmpform.components.asyncLoad
import org.koin.compose.koinInject

@Composable
fun recipient(
    component: ViewRecipientComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val nav : StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        asyncLoad(status) {

        }
    }
}
