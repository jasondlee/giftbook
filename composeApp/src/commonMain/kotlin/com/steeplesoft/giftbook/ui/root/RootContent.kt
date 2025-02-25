package com.steeplesoft.giftbook.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.steeplesoft.giftbook.ui.clickme.clickMe
import com.steeplesoft.giftbook.ui.drawer.MyBottomBar
import com.steeplesoft.mobile.drawer.DrawerContent
import com.steeplesoft.mobile.drawer.MyTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                MyTopAppBar {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            drawerContent = {
                DrawerContent { itemLabel ->
//                    component.onNavigate()
                    coroutineScope.launch {
                        // delay for the ripple effect
                        delay(timeMillis = 250)
                        scaffoldState.drawerState.close()
                    }
                }
            },
            bottomBar = {
                MyBottomBar { navItem -> { } }
            },
        ) { _ ->
            Children(
                stack = component.stack,
                modifier = modifier,
                animation = stackAnimation(slide()),
            ) {
                val childModifier = Modifier.fillMaxSize()
                when (val child = it.instance) {
                    is RootComponent.Child.ClickMe -> clickMe(child.component, childModifier)
                }
            }
        }
    }
}
