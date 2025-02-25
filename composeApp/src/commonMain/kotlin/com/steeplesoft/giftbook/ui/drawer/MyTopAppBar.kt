@file:OptIn(ExperimentalMaterial3Api::class)

package com.steeplesoft.mobile.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
