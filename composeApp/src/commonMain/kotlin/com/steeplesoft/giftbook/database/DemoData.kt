package com.steeplesoft.giftbook.database

import com.steeplesoft.giftbook.model.EventType
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.giftbook.model.Recipient
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
        listOf(
            GiftIdea(0, "Idea 1", null, 1, null, 10),
            GiftIdea(0, "Idea 2", null, 1, null, 15),
            GiftIdea(0, "Idea 3", null, 1, null, 25),
            GiftIdea(0, "Idea 1", null, 2, 2, 35, 42),
            GiftIdea(0, "Idea 2", null, 2, null, 5),
            GiftIdea(0, "Idea 1", null, 5, 4, 5, 2),
            GiftIdea(0, "Idea 2", null, 5, 4, 15, 18),
            GiftIdea(0, "Idea 3", null, 5, null, 20),
            GiftIdea(0, "Idea 1", null, 6, null, 10),
            GiftIdea(0, "Idea 2", null, 6, 4, 20, 28),
            GiftIdea(0, "Idea 3", null, 6, null, 5),
            GiftIdea(0, "Idea 4", null, 1, null, 25),
            GiftIdea(0, "Idea 5", null, 1, null, 25),
            GiftIdea(0, "Idea 6", null, 1, null, 25),
            GiftIdea(0, "Idea 7", null, 1, null, 25),
            GiftIdea(0, "Idea 8", null, 1, null, 25),
        ).forEach { dao.insert(it) }
    }
}

private suspend fun loadOccasions(database: AppDatabase) {
    val dao = database.occasionDao()
    if (dao.getAll().isEmpty()) {
        listOf(
            Occasion(3, "Christmas 2024", LocalDate(2024, 12, 25), EventType.CHRISTMAS),
            Occasion(1, "Christmas 2025", LocalDate(2025, 12, 25), EventType.CHRISTMAS),
            Occasion(5, "Christmas 2026", LocalDate(2026, 12, 25), EventType.CHRISTMAS),
            Occasion(8, "Christmas 2027", LocalDate(2027, 12, 25), EventType.CHRISTMAS),

            Occasion(2, "Laura's Birthday 2025", LocalDate(2025, 8, 1), EventType.BIRTHDAY),
            Occasion(6, "Fenton's Birthday 2025", LocalDate(2025, 11, 21), EventType.BIRTHDAY),

            Occasion(4, "Valentine's Day 2026", LocalDate(2026, 2, 14), EventType.VALENTINES),
            Occasion(7, "Valentine's Day 2027", LocalDate(2027, 2, 14), EventType.VALENTINES),

            Occasion(9, "College Graduation", LocalDate(2030, 5, 14), EventType.GRADUATION),
        ).forEach {
            dao.insert(it)
        }

        dao.addRecipients(
            OccasionRecipient(2, 2, 5, 150),
            OccasionRecipient(6, 1, 3, 35),
        )
        (1L..8).map { recip ->
            dao.addRecipients(
                OccasionRecipient(1, recip, 3, 35),
                OccasionRecipient(3, recip, 3, 35),
                OccasionRecipient(4, recip, 3, 35),
                OccasionRecipient(5, recip, 3, 35),
                OccasionRecipient(7, recip, 3, 35),
                OccasionRecipient(8, recip, 3, 35)
            )
        }.toTypedArray()
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
            Recipient(7, "Tony Prito"),
            Recipient(8, "Biff Hooper")
        )
    }
}
