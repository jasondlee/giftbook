package com.steeplesoft.giftbook.ui.clickme

import com.arkivanov.decompose.ComponentContext

interface ClickMeComponent {
}


class DefaultClickMeComponent(componentContext: ComponentContext) : ClickMeComponent,
    ComponentContext by componentContext {
}
