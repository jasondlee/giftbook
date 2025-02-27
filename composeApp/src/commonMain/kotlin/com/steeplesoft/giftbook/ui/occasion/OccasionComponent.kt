package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.root.NavigationConfig
import com.steeplesoft.giftbook.ui.root.nav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface OccasionComponent {
    val occasion: Occasion
    fun edit()
    fun delete()
}

class DefaultOccasionComponent(
    componentContext: ComponentContext,
    override val occasion: Occasion
) : OccasionComponent,
    ComponentContext by componentContext {
    override fun edit() {
        nav.pushToFront(NavigationConfig.AddEditOccasion(occasion))
    }

    override fun delete() {
        CoroutineScope(Dispatchers.Main).launch {
            db.occasionDao().delete(occasion)

            nav.pop()
        }
    }
}
