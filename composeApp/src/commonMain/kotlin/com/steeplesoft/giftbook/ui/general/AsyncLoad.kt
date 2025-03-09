package com.steeplesoft.giftbook.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun asyncLoad(status: Status,
              content: @Composable () -> Unit) {
    when (status) {
        Status.LOADING -> {
            LoadingScreen()
        }

        Status.SUCCESS -> {
            content()
        }

        Status.ERROR -> {
            Text("Error loading meals")
        }
    }
}
