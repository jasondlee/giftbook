package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddEditOccasionComponent(
    componentContext: ComponentContext,
    val occasion: Occasion?
) : ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val occasionDao : OccasionDao by inject()

    val form = OccasionForm(occasion)

    fun save() {
        CoroutineScope(Dispatchers.Main).launch {
            val newOccasion = Occasion(
                occasion?.id ?: 0,
                form.name.state.value!!,
                form.eventDate.state.value!!
            )

            if (occasion == null) {
                newOccasion.id = occasionDao.insert(newOccasion)
            } else {
                occasionDao.update(newOccasion)
            }

            nav.pop()
            nav.bringToFront(NavigationConfig.ViewOccasion(newOccasion))
        }
    }
}
