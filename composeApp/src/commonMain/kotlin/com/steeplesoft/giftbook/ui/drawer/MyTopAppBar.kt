package com.steeplesoft.mobile.drawer

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun MyTopAppBar(
//    currentScreen: SteeplesoftDestination,
    onNavIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Navigation Drawer"
                )
            }
        }
    )
}
