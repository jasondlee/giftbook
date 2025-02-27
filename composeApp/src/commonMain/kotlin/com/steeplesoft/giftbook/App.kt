package com.steeplesoft.giftbook

import androidx.compose.runtime.Composable
import com.example.compose.AppTheme
import com.steeplesoft.giftbook.ui.root.RootComponent
import com.steeplesoft.giftbook.ui.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    AppTheme {
        RootContent(rootComponent)
    }
}
