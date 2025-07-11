package com.steeplesoft.giftbook.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.home.HomeComponent
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionListComponent
import com.steeplesoft.giftbook.ui.occasion.ViewOccasionComponent
import com.steeplesoft.giftbook.ui.occasionRecip.AddEditOccasionRecipientComponent
import com.steeplesoft.giftbook.ui.occasionRecip.ViewOccasionRecipient
import com.steeplesoft.giftbook.ui.recipients.AddEditRecipientComponent
import com.steeplesoft.giftbook.ui.recipients.RecipientListComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()

    val stack: Value<ChildStack<*, ComponentContext>> = childStack(
        source = nav,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.Home(),
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(config: NavigationConfig, componentContext: ComponentContext): ComponentContext {
        return when (config) {
            is NavigationConfig.Home -> HomeComponent(componentContext, config.occasion)
            is NavigationConfig.Occasions -> OccasionListComponent(componentContext)
            is NavigationConfig.ViewOccasion -> ViewOccasionComponent(componentContext, config.occasion)
            is NavigationConfig.AddEditOccasion -> AddEditOccasionComponent(componentContext, config.occasion)
            is NavigationConfig.ViewOccasionRecipient -> ViewOccasionRecipient(componentContext, config.recipId, config.occasionId)
            is NavigationConfig.AddEditOccasionRecipient -> AddEditOccasionRecipientComponent(componentContext, config.occasion, config.recipient, config.occasionRecip)
            is NavigationConfig.Recipients -> RecipientListComponent(componentContext)
            is NavigationConfig.AddEditRecipient -> AddEditRecipientComponent(componentContext, config.recipient)
            is NavigationConfig.ViewRecipient -> TODO()
        }
    }
}
