package com.steeplesoft.giftbook.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.camper.components.Status
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionProgress
import com.steeplesoft.giftbook.model.toOccasionProgress
import com.steeplesoft.giftbook.ui.componentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeComponent(
    componentContext: ComponentContext,
    var occasionId: Long? = null
) : ComponentContext by componentContext, KoinComponent {
    private val occasionDao: OccasionDao by inject()
    private val nav: StackNavigation<NavigationConfig> by inject()

    val occasions = MutableValue(listOf<Occasion>())
    val requestStatus = MutableValue(Status.LOADING)
    val occasionProgress: MutableValue<List<OccasionProgress>> = MutableValue(emptyList())
    val occasion = MutableValue<Occasion?>(null)
    private val scope = componentContext.componentScope()

    init {
        componentContext.doOnResume {
            scope.launch(Dispatchers.IO) {
                requestStatus.update { Status.LOADING }

                val list = occasionDao.getFutureOccasions()
                val selectedOccasion =
                    if (occasionId != null)
                        occasionDao.getOccasion(occasionId!!)
                    else
                        list.firstOrNull()

                occasions.update { list }
                occasion.update { selectedOccasion }

                selectedOccasion?.let {
                    onOccasionChange(it)
                }

                requestStatus.update { Status.SUCCESS }
            }
        }
    }

    fun onOccasionChange(newValue: Occasion) {
        scope.launch(Dispatchers.IO) {
            occasion.update { newValue }
                val list = occasionDao.getProgress(newValue.id).map { it.toOccasionProgress() }

            occasionProgress.update { list }
        }
    }

    fun addRecipient() {
        occasion?.let {
            nav.pushToFront(NavigationConfig.AddEditOccasionRecipient(it))
        }
    }
}
