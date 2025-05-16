@file:OptIn(ExperimentalMaterial3Api::class)

package com.steeplesoft.giftbook

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.steeplesoft.giftbook.ui.root.RootComponent

fun MainViewController() = ComposeUIViewController {
    val rootComponent = remember {
        RootComponent(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(rootComponent)
}
