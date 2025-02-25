package com.steeplesoft.giftbook.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.steeplesoft.giftbook.ui.clickme.clickMe
import com.steeplesoft.giftbook.ui.drawer.BottomNavBar
import com.steeplesoft.mobile.drawer.MyTopAppBar
import kotlinx.coroutines.launch

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MyTopAppBar {
                    coroutineScope.launch {
                    }
                }
            },
            bottomBar = {
                BottomNavBar { navItem -> { } }
            },
        ) { innerPadding ->
            Children(
                stack = component.stack,
                modifier = modifier.padding(innerPadding).padding(top = 10.dp),
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
