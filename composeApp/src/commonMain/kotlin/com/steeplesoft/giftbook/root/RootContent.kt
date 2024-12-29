package com.steeplesoft.giftbook.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.steeplesoft.giftbook.clickme.clickMe

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
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
