package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.steeplesoft.giftbook.database.db
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

interface OccasionComponent {
    val occasions : List<Occasion>
    var requestStatus : MutableValue<Status>

}

class DefaultOccasionComponent(
    componentContext: ComponentContext
) : OccasionComponent,
    ComponentContext by componentContext {
    override var occasions = emptyList<Occasion>()
    override var requestStatus  = MutableValue(Status.LOADING)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            occasions = db.occasionDao().getFutureOccasions()

            requestStatus.update { Status.SUCCESS }
        }
    }
}
