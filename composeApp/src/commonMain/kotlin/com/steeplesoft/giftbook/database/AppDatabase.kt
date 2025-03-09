package com.steeplesoft.giftbook.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.Recipient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

// https://developer.android.com/kotlin/multiplatform/room

internal const val dbFileName = "giftbook.db"

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

val db = getRoomDatabase()

fun getRoomDatabase(): AppDatabase {
    val database = getDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

    loadDemoData(database)

    return database
}

@Database(
    entities = [Occasion::class, GiftIdea::class, Recipient::class, OccasionRecipient::class], version = 1
)
@TypeConverters(LocalDateConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun occasionDao(): OccasionDao
    abstract fun recipientDao(): RecipientDao
    abstract fun giftIdeaDao(): GiftIdeaDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}


