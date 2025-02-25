package com.steeplesoft.giftbook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.steeplesoft.giftbook.ui.root.RootComponent
import com.steeplesoft.giftbook.ui.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(rootComponent)
    }
}
