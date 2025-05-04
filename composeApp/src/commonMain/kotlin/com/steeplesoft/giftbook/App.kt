package com.steeplesoft.giftbook

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.steeplesoft.giftbook.theme.AppTheme
import com.steeplesoft.giftbook.ui.root.RootComponent
import com.steeplesoft.giftbook.ui.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
@Preview
fun App(rootComponent: RootComponent) {
    AppTheme {
        RootContent(rootComponent)
    }
}
