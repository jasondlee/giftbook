package com.steeplesoft.giftbook.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushToFront
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.ui.drawer.BottomNavBar
import com.steeplesoft.giftbook.ui.home.Home
import com.steeplesoft.giftbook.ui.home.HomeComponent
import com.steeplesoft.giftbook.ui.idea.AddEditIdea
import com.steeplesoft.giftbook.ui.idea.AddEditIdeaComponent
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasion
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionList
import com.steeplesoft.giftbook.ui.occasion.OccasionListComponent
import com.steeplesoft.giftbook.ui.occasion.ViewOccasion
import com.steeplesoft.giftbook.ui.occasion.ViewOccasionComponent
import com.steeplesoft.giftbook.ui.occasionRecip.AddEditOccasionRecipient
import com.steeplesoft.giftbook.ui.occasionRecip.AddEditOccasionRecipientComponent
import com.steeplesoft.giftbook.ui.occasionRecip.ViewOccasionRecip
import com.steeplesoft.giftbook.ui.occasionRecip.ViewOccasionRecipient
import com.steeplesoft.giftbook.ui.recipients.AddEditRecipientComponent
import com.steeplesoft.giftbook.ui.recipients.AddEditRecipientContent
import com.steeplesoft.giftbook.ui.recipients.RecipientList
import com.steeplesoft.giftbook.ui.recipients.RecipientListComponent
import com.steeplesoft.giftbook.ui.recipients.ViewRecipient
import com.steeplesoft.giftbook.ui.recipients.ViewRecipientComponent
import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@ExperimentalMaterial3Api
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    val nav : StackNavigation<NavigationConfig> = koinInject()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            text = stringResource(Res.string.app_name),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            },
            bottomBar = {
                BottomNavBar(onNavigate = { navItem ->
                    {
                        nav.pushToFront(navItem.route)
                    }
                })
            },
        ) { innerPadding ->
            Children(
                stack = component.stack,
                modifier = modifier.padding(innerPadding).padding(5.dp),
                animation = stackAnimation(slide()),
            ) {
                val childModifier = modifier.fillMaxWidth().padding(10.dp)
                when (val component = it.instance) {
                    is HomeComponent -> Home(component, childModifier)
                    is OccasionListComponent -> OccasionList(component, childModifier)
                    is ViewOccasionComponent -> ViewOccasion(component, childModifier)
                    is AddEditOccasionComponent -> AddEditOccasion(component, childModifier)
                    is ViewOccasionRecipient -> ViewOccasionRecip(component, childModifier)
                    is AddEditOccasionRecipientComponent -> AddEditOccasionRecipient(component, childModifier)
                    is RecipientListComponent -> RecipientList(component, childModifier)
                    is ViewRecipientComponent -> ViewRecipient(component, childModifier)
                    is AddEditRecipientComponent -> AddEditRecipientContent(component, childModifier)
                    is AddEditIdeaComponent -> AddEditIdea(component, childModifier)
                    else -> {
                        Text("Unknown child: ${it.instance}")
                    }
                }
            }
        }
    }
}
