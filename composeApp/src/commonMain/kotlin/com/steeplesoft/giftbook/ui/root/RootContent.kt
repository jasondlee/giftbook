package com.steeplesoft.giftbook.ui.root

import androidx.compose.foundation.layout.fillMaxSize
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
import com.steeplesoft.giftbook.ui.drawer.BottomNavBar
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.home.HomeComponent
import com.steeplesoft.giftbook.ui.home.home
import com.steeplesoft.giftbook.ui.occasion.AddEditOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.OccasionListComponent
import com.steeplesoft.giftbook.ui.occasion.ViewOccasionComponent
import com.steeplesoft.giftbook.ui.occasion.addEditOccasion
import com.steeplesoft.giftbook.ui.occasion.occasionList
import com.steeplesoft.giftbook.ui.occasion.viewOccasion
import com.steeplesoft.giftbook.ui.occasionRecip.AddEditOccasionRecipientComponent
import com.steeplesoft.giftbook.ui.occasionRecip.ViewOccasionRecipient
import com.steeplesoft.giftbook.ui.occasionRecip.addEditOccasionRecipient
import com.steeplesoft.giftbook.ui.occasionRecip.viewOccasionRecip
import com.steeplesoft.giftbook.ui.recipients.RecipientListComponent
import com.steeplesoft.giftbook.ui.recipients.recipientList
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
                modifier = modifier.padding(innerPadding).padding(top = 10.dp),
                animation = stackAnimation(slide()),
            ) {
                val childModifier = modifier.fillMaxSize()
                    .padding(10.dp)
                when (val component = it.instance) {
                    is HomeComponent -> home(component, childModifier)
                    is OccasionListComponent -> occasionList(component, childModifier)
                    is ViewOccasionComponent -> viewOccasion(component, childModifier)
                    is AddEditOccasionComponent -> addEditOccasion(component, childModifier)
                    is ViewOccasionRecipient -> viewOccasionRecip(component, childModifier)
                    is AddEditOccasionRecipientComponent -> addEditOccasionRecipient(component, childModifier)
                    is RecipientListComponent -> recipientList(component, childModifier)
                }
            }
        }
    }
}
