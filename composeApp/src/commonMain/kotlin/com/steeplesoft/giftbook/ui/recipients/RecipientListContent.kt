package com.steeplesoft.giftbook.ui.recipients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.occasion.DividingLine
import com.steeplesoft.kmpform.components.asyncLoad
import org.koin.compose.koinInject

@Composable
fun recipientList(
    component: RecipientListComponent,
    modifier: Modifier = Modifier
) {
    val status by component.requestStatus.subscribeAsState()
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    Column(
        modifier = modifier.padding(10.dp),
    ) {
        asyncLoad(status) {
            val recipients by component.recipients.subscribeAsState()
            LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                item {
                    Text("Recipients", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                }
                items(recipients) { recipient ->
                    Column(modifier = Modifier.clickable {
//                        nav.bringToFront(NavigationConfig.ViewOccasion(recipient))
                    }) {
                        Text(
                            fontSize = 24.sp,
                            text = recipient.name
                        )
                    }
                    DividingLine()
                }
            }
            ActionButton(
                onClick = {
//                    nav.bringToFront(NavigationConfig.AddEditOccasion())
                }
            )

        }
    }
}
