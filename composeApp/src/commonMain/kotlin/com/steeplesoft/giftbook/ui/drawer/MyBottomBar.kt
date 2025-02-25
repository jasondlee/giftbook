package com.steeplesoft.giftbook.ui.drawer

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.steeplesoft.mobile.drawer.NavigationItem

@Composable
fun MyBottomBar(
    onNavigate: (NavigationItem) -> Unit
) {
    val items = listOf(
        NavigationItem.Back,
        NavigationItem.Home,
    )
    BottomNavigation(
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.imageVector, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    onNavigate(item)
                }
            )
        }
    }
}
