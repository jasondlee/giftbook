@file:Suppress("SpellCheckingInspection")

package com.steeplesoft.giftbook.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.home.DefaultHomeComponent
import com.steeplesoft.giftbook.ui.home.HomeComponent
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.DefaultAddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.DefaultOccasionListComponent
import com.steeplesoft.giftbook.ui.occasion.DefaultViewOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionListComponent
import com.steeplesoft.giftbook.ui.occasion.ViewOccasionComponent
import com.steeplesoft.giftbook.ui.occasionRecip.DefaultViewOccasionRecipient
import com.steeplesoft.giftbook.ui.occasionRecip.ViewOccasionRecipient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
        class Occasions(val component: OccasionListComponent) : Child
        class ViewOccasion(val component: ViewOccasionComponent) : Child
        class AddEditOccasion(val component: AddEditOccasionComponent) : Child
        class ViewOccasionRecip(val component: ViewOccasionRecipient) : Child
    }
}

class DefaultRootComponent(componentContext: ComponentContext) :
    RootComponent,
    ComponentContext by componentContext,
    KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()

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
            is NavigationConfig.Home ->
                RootComponent.Child.Home(DefaultHomeComponent(componentContext))
            is NavigationConfig.Occasions ->
                RootComponent.Child.Occasions(DefaultOccasionListComponent(componentContext))
            is NavigationConfig.ViewOccasion ->
                RootComponent.Child.ViewOccasion(DefaultViewOccasionComponent(componentContext, config.occasion))
            is NavigationConfig.AddEditOccasion ->
                RootComponent.Child.AddEditOccasion(DefaultAddEditOccasionComponent(componentContext, config.occasion))
            is NavigationConfig.ViewOccasionRecip ->
                RootComponent.Child.ViewOccasionRecip(DefaultViewOccasionRecipient(componentContext, config.recipId, config.occasionId))
        }
    }
}
