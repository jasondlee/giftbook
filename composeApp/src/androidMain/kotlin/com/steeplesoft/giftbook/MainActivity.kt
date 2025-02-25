package com.steeplesoft.giftbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.steeplesoft.giftbook.ui.root.DefaultRootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Always create the root component outside Compose on the main thread
        val rootComponent = DefaultRootComponent(defaultComponentContext())

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}
