package com.steeplesoft.giftbook.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.ui.clickme.ClickMeComponent
import com.steeplesoft.giftbook.ui.clickme.DefaultClickMeComponent
import com.steeplesoft.giftbook.ui.home.DefaultHomeComponent
import com.steeplesoft.giftbook.ui.home.HomeComponent
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.DefaultAddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.DefaultOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.DefaultOccasionsComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionsComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class ClickMe(val component: ClickMeComponent) : Child
        class Home(val component: HomeComponent) : Child
        class Occasions(val component: OccasionsComponent) : Child
        class ViewOccasion(val component: OccasionComponent) : Child
        class AddEditOccasion(val component: AddEditOccasionComponent) : Child
    }
}

class DefaultRootComponent(componentContext: ComponentContext) :
    RootComponent, ComponentContext by componentContext {

    init {
        db.occasionDao()
    }

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.Home,
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
            is NavigationConfig.Home ->
                RootComponent.Child.Home(DefaultHomeComponent(componentContext))
            is NavigationConfig.Occasions ->
                RootComponent.Child.Occasions(DefaultOccasionsComponent(componentContext))
            is NavigationConfig.ViewOccasion ->
                RootComponent.Child.ViewOccasion(DefaultOccasionComponent(componentContext, config.occasion))
            is NavigationConfig.AddEditOccasion ->
                RootComponent.Child.AddEditOccasion(DefaultAddEditOccasionComponent(componentContext, config.occasion))
        }
    }
}
