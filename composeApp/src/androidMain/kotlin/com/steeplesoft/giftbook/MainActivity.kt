package com.steeplesoft.giftbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.arkivanov.decompose.defaultComponentContext
import com.steeplesoft.giftbook.ui.root.RootComponent

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Always create the root component outside Compose on the main thread
        val rootComponent = RootComponent(defaultComponentContext())

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}
