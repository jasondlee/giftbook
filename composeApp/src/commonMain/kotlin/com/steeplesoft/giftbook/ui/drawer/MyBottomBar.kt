package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.steeplesoft.giftbook.NavigationConfig
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
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
        val iconSize = 36.dp
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(iconSize),
                    tint = MaterialTheme.colorScheme.primary)
            },
            alwaysShowLabel = false,
            selected = false,
            onClick = { nav.pop() }
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        contentDescription = item.title,
                        imageVector = item.image,
                        modifier = Modifier.size(iconSize),
                        tint = MaterialTheme.colorScheme.primary)
                },
                alwaysShowLabel = false,
                selected = false,
                onClick = { nav.pushToFront(item.route) }
            )
        }
    }
}
