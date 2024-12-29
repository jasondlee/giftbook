package com.steeplesoft.giftbook.clickme

import com.arkivanov.decompose.ComponentContext

interface ClickMeComponent {
}


class DefaultClickMeComponent(componentContext: ComponentContext) : ClickMeComponent,
    ComponentContext by componentContext {
}
