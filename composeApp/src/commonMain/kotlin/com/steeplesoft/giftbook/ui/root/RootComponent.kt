package com.steeplesoft.giftbook.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.ui.home.HomeComponent
import com.steeplesoft.giftbook.ui.idea.AddEditIdeaComponent
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionListComponent
import com.steeplesoft.giftbook.ui.occasion.ViewOccasionComponent
import com.steeplesoft.giftbook.ui.occasionRecip.AddEditOccasionRecipientComponent
import com.steeplesoft.giftbook.ui.occasionRecip.ViewOccasionRecipient
import com.steeplesoft.giftbook.ui.recipients.AddEditRecipientComponent
import com.steeplesoft.giftbook.ui.recipients.RecipientListComponent
import com.steeplesoft.giftbook.ui.recipients.ViewRecipientComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RootComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val occasionDao : OccasionDao by inject()

    val stack: Value<ChildStack<*, ComponentContext>> = childStack(
        source = nav,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.Home(),
        handleBackButton = true,
        childFactory = ::child,
    )

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.Main).launch {
                val list = occasionDao.getFutureOccasions()
                if (list.isEmpty()) {
                    nav.bringToFront(NavigationConfig.AddEditOccasion())
                }
            }
        }
    }

    private fun child(config: NavigationConfig, componentContext: ComponentContext): ComponentContext {
        return when (config) {
            is NavigationConfig.Home -> HomeComponent(componentContext, config.occasionId)
            is NavigationConfig.Occasions -> OccasionListComponent(componentContext)
            is NavigationConfig.Recipients -> RecipientListComponent(componentContext)

            is NavigationConfig.ViewOccasion -> ViewOccasionComponent(componentContext, config.occasionId)
            is NavigationConfig.AddEditOccasion -> AddEditOccasionComponent(componentContext, config.occasion)
            is NavigationConfig.ViewOccasionRecipient -> ViewOccasionRecipient(componentContext, config.recipId, config.occasionId)
            is NavigationConfig.AddEditOccasionRecipient -> AddEditOccasionRecipientComponent(componentContext, config.occasion, config.recipient, config.occasionRecip)
            is NavigationConfig.AddEditRecipient -> AddEditRecipientComponent(componentContext, config.recipient)
            is NavigationConfig.ViewRecipient -> ViewRecipientComponent(componentContext, config.recipientId)
            is NavigationConfig.AddEditIdea -> AddEditIdeaComponent(componentContext, config.recipient, config.idea)
        }
    }
}
