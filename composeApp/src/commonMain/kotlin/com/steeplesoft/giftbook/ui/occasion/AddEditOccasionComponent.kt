package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.pop
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.root.NavigationConfig
import com.steeplesoft.giftbook.ui.root.nav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface AddEditOccasionComponent {
    val occasion: Occasion?
    val form: OccasionForm
    fun save(): Unit
}

class DefaultAddEditOccasionComponent(
    componentContext: ComponentContext,
    override val occasion: Occasion?
) : AddEditOccasionComponent,
    ComponentContext by componentContext {
    override val form = OccasionForm(occasion)

    override fun save() {
        CoroutineScope(Dispatchers.Main).launch {
            val newOccasion = Occasion(
                occasion?.id ?: 0,
                form.name.state.value!!,
                form.eventDate.state.value!!
            )

            if (occasion == null) {
                db.occasionDao().insertAll(newOccasion)
            } else {
                db.occasionDao().update(newOccasion)
            }

            nav.pop()
            nav.bringToFront(NavigationConfig.ViewOccasion(newOccasion))
        }
    }
}
