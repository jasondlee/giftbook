package com.steeplesoft.giftbook.database

import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.Recipient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.LocalDate

val mutex = Mutex()
fun loadDemoData(database: AppDatabase) {
    CoroutineScope(Dispatchers.IO).launch {
        mutex.withLock {
            loadRecipients(database)
            loadOccasions(database)
            loadGiftIdes(database)
        }
    }
}

private suspend fun loadGiftIdes(database: AppDatabase) {
    val dao = database.giftIdeaDao()
    if (dao.getAll().isEmpty()) {
        dao.insertAll(
            GiftIdea(0, "Idea 1", null, 1, null, 10f),
            GiftIdea(0, "Idea 2", null, 1, null, 15f),
            GiftIdea(0, "Idea 3", null, 1, null, 25f),
            GiftIdea(0, "Idea 1", null, 2, 2, 35f, 42f),
            GiftIdea(0, "Idea 2", null, 2, null, 5f),
            GiftIdea(0, "Idea 1", null, 5, 4, 5f, 2f),
            GiftIdea(0, "Idea 2", null, 5, 4, 15f, 18f),
            GiftIdea(0, "Idea 3", null, 5, null, 20f),
            GiftIdea(0, "Idea 1", null, 6, null, 10f),
            GiftIdea(0, "Idea 2", null, 6, 4, 20f,28f),
            GiftIdea(0, "Idea 3", null, 6, null, 5f),
        )
    }
}

private suspend fun loadOccasions(database: AppDatabase) {
    val dao = database.occasionDao()
    if (dao.getAll().isEmpty()) {
        listOf(
            Occasion(1, "Christmas 2025", LocalDate(2025,12,25)),
            Occasion(2, "Laura's Birthday", LocalDate(2025,8,1)),
            Occasion(3, "Christmas 2024", LocalDate(2024,12,25)),
            Occasion(4, "Valentine's Day", LocalDate(2026,2,14)),
        ).forEach {
            dao.insert(it)
        }
        dao.addRecipients(
            OccasionRecipient(1, 1, 5, 150f),
            OccasionRecipient(1, 2, 5, 150f),
            OccasionRecipient(1, 3, 5, 150f),
            OccasionRecipient(1, 4, 5, 150f),
            OccasionRecipient(2, 2, 5, 150f),
            OccasionRecipient(4, 2, 5, 150f),
            OccasionRecipient(4, 5, 3, 35f),
            OccasionRecipient(4, 6, 3, 35f),
        )
    }
}

private suspend fun loadRecipients(database: AppDatabase) {
    val dao = database.recipientDao()
    if (dao.getAll().isEmpty()) {
        dao.insertAll(
            Recipient(1, "Fenton"),
            Recipient(2, "Laura"),
            Recipient(3, "Frank"),
            Recipient(4, "Joe"),
            Recipient(5, "Iola Morton"),
            Recipient(6, "Callie Shaw"),
        )
    }
}
