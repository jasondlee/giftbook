package com.steeplesoft.giftbook.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.ui.clickme.ClickMeComponent
import com.steeplesoft.giftbook.ui.clickme.DefaultClickMeComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class ClickMe(val component: ClickMeComponent) : Child
    }
}

class DefaultRootComponent(componentContext: ComponentContext) :
    RootComponent, ComponentContext by componentContext {
    private val nav = StackNavigation<NavigationConfig>()

    init {
        db.occasionDao()
    }

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.ClickMe,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: NavigationConfig,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is NavigationConfig.ClickMe ->
                RootComponent.Child.ClickMe(DefaultClickMeComponent(componentContext))
        }
    }
}

@Serializable
sealed interface NavigationConfig {
    @Serializable
    data object ClickMe : NavigationConfig
}
