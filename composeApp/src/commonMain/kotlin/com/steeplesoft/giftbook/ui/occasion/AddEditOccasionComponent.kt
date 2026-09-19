package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.form.OccasionForm
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.ui.componentScope
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
    private val scope = componentContext.componentScope()

    val form = OccasionForm(occasion)

    fun save() {
        scope.launch {
            form.validate()
            val name = form.name.state.value?.trim().orEmpty()
            val eventDate = form.eventDate.state.value
            val eventType = form.eventType.state.value?.eventType
            if (!form.isValid || name.isEmpty() || eventDate == null || eventType == null) return@launch

            val newOccasion = Occasion(
                id = occasion?.id ?: 0,
                name = name,
                eventDate = eventDate,
                eventType = eventType
            )

            if (occasion == null) {
                newOccasion.id = occasionDao.insert(newOccasion)
            } else {
                occasionDao.update(newOccasion)
            }

            nav.pop()
        }
    }

    fun cancel() {
        scope.launch {
            nav.pop()
        }
    }
}
