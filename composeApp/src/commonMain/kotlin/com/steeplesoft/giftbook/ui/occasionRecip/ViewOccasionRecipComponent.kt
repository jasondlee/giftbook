package com.steeplesoft.giftbook.ui.occasionRecip

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.doOnResume
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.Recipient
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.kmpform.components.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewOccasionRecipient(
    componentContext: ComponentContext,
    private val recipId: Long,
    private val occasionId: Long
) : ComponentContext by componentContext, KoinComponent {
    private val nav : StackNavigation<NavigationConfig> by inject()
    private val occasionDao : OccasionDao by inject()
    private val recipientDao : RecipientDao by inject()
    private val giftIdeaDao : GiftIdeaDao by inject()

    lateinit var occasionRecip: OccasionRecipient
    lateinit var recip: Recipient
    lateinit var occasion: Occasion

    var gifts: MutableValue<List<GiftIdea>> = MutableValue(emptyList())
    var requestStatus: MutableValue<Status> = MutableValue(Status.LOADING)

    init {
        componentContext.doOnResume {
            CoroutineScope(Dispatchers.IO).launch {
                occasionRecip = recipientDao.getRecipientForOccasion(occasionId, recipId)
                recip = recipientDao.getRecipient(recipId)
                occasion = occasionDao.getOccasion(occasionId)
                val list = giftIdeaDao.lookupIdeasByRecipAndOccasion(recipId, occasionId)
                gifts.update { list }
                requestStatus.update { Status.SUCCESS }
            }
        }
    }


    fun edit() {
        nav.pushToFront(NavigationConfig.AddEditOccasionRecipient(occasion, recip, occasionRecip))
    }

    fun delete() {
        CoroutineScope(Dispatchers.Main).launch {
//            db.occasionDao().delete(occasion)
            occasionDao.deleteOccasionRecip(occasionRecip)

            nav.pop()
            nav.pop()
            nav.pushNew(NavigationConfig.Home(occasion))
        }
    }

    fun giftGiven(giftId: Long, cost: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            val orig = gifts.value
            val gift = orig.first { it.id == giftId }

            gift.occasionId = occasionId
            gift.actualCost = cost

            giftIdeaDao.update(gift)

            /*
             * This seems really odd, but if I get gifts.value, change the list, then call gifts.update, nothing happens. The only
             * way I've found to get the UI to update is to set gifts to an empty list, then back to the real list. Surely I'm just
             * missing something, but this works for now, so I'll revisit this later when I have more time. -- jdl 2025/03/16
             */
            gifts.update { emptyList() }
            gifts.update { orig }
        }
    }

    fun resetGiftGiven(giftId: Long) {
        CoroutineScope(Dispatchers.IO).launch {

            val orig = gifts.value
            val gift = orig.first { it.id == giftId }

            gift.occasionId = null
            gift.actualCost = null

            giftIdeaDao.update(gift)

            /*
             * This seems really odd, but if I get gifts.value, change the list, then call gifts.update, nothing happens. The only
             * way I've found to get the UI to update is to set gifts to an empty list, then back to the real list. Surely I'm just
             * missing something, but this works for now, so I'll revisit this later when I have more time. -- jdl 2025/03/16
             */
            gifts.update { emptyList() }
            gifts.update { orig }
        }
    }
}
