package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.steeplesoft.giftbook.NavigationConfig
import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.back
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun BottomNavBar(
    onNavigate: (NavigationItem) -> Unit
) {
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Occasions,
        NavigationItem.Recipients
    )
    NavigationBar/*(containerColor = mainColor)*/ {
        val labelSize = 10.sp
        //getting the list of bottom navigation items for our data class
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(Res.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.size(48.dp)
                )
            },
            label = { Text(text = "Back:", fontSize = labelSize) },
            alwaysShowLabel = false,
            selected = false,
            onClick = { nav.pop() }
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(item.image),
                        contentDescription = item.title,
                        modifier = Modifier.size(48.dp)
                    )
                },
                label = { Text(text = item.title, fontSize = labelSize) },
                alwaysShowLabel = false,
                selected = false,
                onClick = { nav.pushToFront(item.route) }
            )
        }
    }
}
