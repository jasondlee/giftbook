package com.steeplesoft.giftbook.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipientCrossRef
import com.steeplesoft.giftbook.database.model.Recipient
import giftbook.composeapp.generated.resources.Res
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.jetbrains.compose.resources.ExperimentalResourceApi

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
    entities = [
        Occasion::class,
        GiftIdea::class,
        Recipient::class,
        OccasionRecipientCrossRef::class,
//        OccasionWithRecipients::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun occasionDao(): OccasionDao
    abstract fun recipientDao(): RecipientDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

val mutex = Mutex()
fun loadDemoData(database: AppDatabase) {
    CoroutineScope(Dispatchers.IO).launch {
        mutex.withLock {
            loadRecipients(database)
            loadOccasions(database)
        }
    }
}

private suspend fun loadOccasions(database: AppDatabase) {
    val dao = database.occasionDao()
    if (dao.getAll().isEmpty()) {
        dao.insertAll(
            Occasion(1, "Christmas"),
            Occasion(2, "Birthday")
        )
        dao.addRecipients(
            OccasionRecipientCrossRef(1, 1),
            OccasionRecipientCrossRef(1, 2),
            OccasionRecipientCrossRef(2, 2),
        )
    }
}

private suspend fun loadRecipients(database: AppDatabase) {
    val dao = database.recipientDao()
    if (dao.getAll().isEmpty()) {
        dao.insertAll(
            Recipient(1, "Jack"),
            Recipient(2, "Jill")
        )
    }
}

class PrepopulateRoomCallback() : RoomDatabase.Callback() {
    @OptIn(ExperimentalResourceApi::class)
    suspend fun readFile(path: String): String {
        val readBytes = Res.readBytes(path)
        return readBytes.decodeToString(0, readBytes.size)
    }

    override fun onCreate(connection: SQLiteConnection) {
        super.onCreate(connection)

        CoroutineScope(Dispatchers.IO).launch {
//            prePopulateUsers()
        }
    }

/*
    private suspend fun prePopulateUsers() {
        try {
            val file = readFile("files/dummyData.json")
            val userList = Json.decodeFromString<List<User>>(file)
            userList.takeIf { it.isNotEmpty() }?.let { list ->
                val userDao = db.userDao()
                list.forEach {
                    userDao.insertAll(it)
                }
                AppLogger.d(TAG, "successfully pre-populated users into database")
            }
        } catch (exception: Exception) {
            AppLogger.e(TAG, exception.message ?: "failed to pre-populate users into database")
        }
    }
*/
}
