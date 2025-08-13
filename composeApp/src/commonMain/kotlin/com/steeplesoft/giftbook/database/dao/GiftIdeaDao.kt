package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.steeplesoft.giftbook.model.GiftIdea

@Dao
interface GiftIdeaDao {
    @Query("SELECT * from GiftIdea")
    suspend fun getAll(): List<GiftIdea>

    @Insert
    @Transaction
    suspend fun insert(idea: GiftIdea): Long

    @Update
    @Transaction
    suspend fun update(gift: GiftIdea)

    @Delete
    @Transaction
    suspend fun delete(gift: GiftIdea)

    @Query("SELECT g.* FROM giftidea g WHERE g.recipientId = :recipId AND g.occasionId IS NULL")
    suspend fun getCurrentGiftIdeasForRecip(recipId: Long): List<GiftIdea>

    @Query("SELECT g.* FROM giftidea g WHERE g.recipientId = :recipId AND g.occasionId IS NOT NULL")
    suspend fun getUsedGiftIdeasForRecip(recipId: Long): List<GiftIdea>

    @Query("""
        SELECT g.*
        FROM GiftIdea g
        WHERE g.recipientId = :recipId
          AND (g.occasionId IS NULL OR g.occasionId = :occasionId)
    """)
    suspend fun lookupIdeasByRecipAndOccasion(recipId: Long, occasionId: Long): List<GiftIdea>
}
