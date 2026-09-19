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
import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.giftbook.theme.IconSize
import com.steeplesoft.giftbook.theme.Typography
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.NavigationConfig
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    selectedRoute: NavigationConfig,
    onNavigate: (NavigationItem) -> Unit
) {
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Occasions,
        NavigationItem.Recipients
    )
    NavigationBar/*(containerColor = mainColor)*/ {
        val iconSize = IconSize.navigationIcon
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
                selected = when (item) {
                    NavigationItem.Home -> selectedRoute.isHomeSection()
                    NavigationItem.Occasions -> selectedRoute.isOccasionSection()
                    NavigationItem.Recipients -> selectedRoute.isRecipientSection()
                },
                onClick = { onNavigate(item) }
            )
        }
    }
}

private fun NavigationConfig.isHomeSection(): Boolean =
    this is NavigationConfig.Home || this is NavigationConfig.ViewOccasionRecipient

private fun NavigationConfig.isOccasionSection(): Boolean =
    this is NavigationConfig.Occasions ||
        this is NavigationConfig.ViewOccasion ||
        this is NavigationConfig.AddEditOccasion ||
        this is NavigationConfig.AddEditOccasionRecipient

private fun NavigationConfig.isRecipientSection(): Boolean =
    this is NavigationConfig.Recipients ||
        this is NavigationConfig.ViewRecipient ||
        this is NavigationConfig.AddEditRecipient ||
        this is NavigationConfig.AddEditIdea
