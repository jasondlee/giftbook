package com.steeplesoft.giftbook

import androidx.room.ColumnInfo
import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.Serializable

// https://developer.android.com/kotlin/multiplatform/room

internal const val dbFileName = "giftbook.db"
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

val db = getRoomDatabase()

fun getRoomDatabase(): AppDatabase {
    val database = getDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    return database
}

@Database(entities = [Recipient::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipientDao(): RecipientDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Entity
@Serializable
data class Recipient(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
)

@Dao
interface RecipientDao {
    @Query("SELECT * FROM Recipient")
    suspend fun getAll(): List<Recipient>

    @Query("SELECT * FROM Recipient WHERE uid IN (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<Recipient>

    @Query("SELECT * FROM Recipient WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Recipient

    @Insert
    suspend fun insertAll(vararg recipients: Recipient)

    @Delete
    suspend fun delete(recipient: Recipient)
}
