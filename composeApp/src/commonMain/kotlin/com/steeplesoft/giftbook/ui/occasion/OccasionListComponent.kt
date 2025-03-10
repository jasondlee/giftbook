package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.ui.general.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface OccasionListComponent {
    val occasions : List<Occasion>
    var requestStatus : MutableValue<Status>
}

class DefaultOccasionListComponent(
    componentContext: ComponentContext
) : OccasionListComponent,
    ComponentContext by componentContext,
    KoinComponent {
    private val occasionDao : OccasionDao by inject()

    override var occasions = emptyList<Occasion>()
    override var requestStatus  = MutableValue(Status.LOADING)
    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                occasions = occasionDao.getFutureOccasions()
                requestStatus.update { Status.SUCCESS }
            }
        }
    }
}
