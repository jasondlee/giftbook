@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.giftbook.model.Recipient

@Dao
interface RecipientDao {
    @Query("SELECT * FROM Recipient ORDER BY name")
    suspend fun getAll(): List<Recipient>

    @Query("SELECT * FROM recipient WHERE id = :id")
    suspend fun getRecipient(id: Long): Recipient

    @Query("SELECT * FROM Recipient WHERE id IN (:ids)")
    suspend fun loadAllByIds(ids: LongArray): List<Recipient>

    @Query("SELECT * FROM Recipient WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Recipient

    @Insert
    @Transaction
    suspend fun insertAll(vararg recipients: Recipient)

    @Update
    @Transaction
    suspend fun update(recipient: Recipient)

    @Delete
    suspend fun delete(recipient: Recipient)

    @Query("SELECT * FROM OccasionRecipient WHERE occasionId = :occasionId and recipientId = :recipientId")
    suspend fun getRecipientForOccasion(occasionId: Long, recipientId: Long): OccasionRecipient

    @Query("SELECT * FROM OccasionRecipient WHERE occasionId = :id")
    suspend fun getRecipientsForOccasion(id: Long): List<OccasionRecipient>

    @Query("""SELECT r.* FROM Recipient r, OccasionRecipient jt, Occasion o
        WHERE occasionId = :id
          AND o.id = occasionId
          AND r.id = recipientId""")
    suspend fun getRecipientListForOccasion(id: Long): List<Recipient>
}
