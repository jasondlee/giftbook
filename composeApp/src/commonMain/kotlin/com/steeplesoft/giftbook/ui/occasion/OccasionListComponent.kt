package com.steeplesoft.giftbook.ui.occasion

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OccasionListComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {
    private val occasionDao : OccasionDao by inject()

    var occasions: MutableValue<List<Occasion>> = MutableValue(mutableListOf())

    var requestStatus  = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                val list = occasionDao.getFutureOccasions()
                occasions.update { list }
                requestStatus.update { Status.SUCCESS }
            }
        }
    }
}
