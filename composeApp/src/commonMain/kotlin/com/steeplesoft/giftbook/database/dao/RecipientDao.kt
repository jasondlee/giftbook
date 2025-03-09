package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.Recipient

@Dao
interface RecipientDao {
    @Query("SELECT * FROM Recipient")
    suspend fun getAll(): List<Recipient>

    @Query("SELECT * FROM recipient WHERE id = :id")
    suspend fun getRecipient(id: Int): Recipient

    @Query("SELECT * FROM Recipient WHERE id IN (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<Recipient>

    @Query("SELECT * FROM Recipient WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Recipient

    @Insert
    @Transaction
    suspend fun insertAll(vararg recipients: Recipient)

    @Delete
    suspend fun delete(recipient: Recipient)

    @Query("SELECT * FROM OccasionRecipient WHERE occasionId = :occasionId and recipientId = :recipientId")
    suspend fun getRecipientForOccasion(occasionId: Int, recipientId: Int): OccasionRecipient

    @Query("SELECT * FROM OccasionRecipient WHERE occasionId = :id")
    suspend fun getRecipientsForOccasion(id: Int): List<OccasionRecipient>

}
