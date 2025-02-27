package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.steeplesoft.giftbook.ui.root.nav

@Composable
fun BottomNavBar(
    onNavigate: (NavigationItem) -> Unit
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Occasions,
        NavigationItem.Recipients,
        NavigationItem.GiftIdeas
    )
    NavigationBar/*(containerColor = mainColor)*/ {
        val labelSize = 10.sp
        //getting the list of bottom navigation items for our data class
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back") },
            label = { Text(text = "Back:", fontSize = labelSize) },
            alwaysShowLabel = true,
            selected = false,
            onClick = { nav.pop() }
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.imageVector, contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = labelSize) },
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    nav.pushToFront(item.route)
                }
            )
        }
    }
}
