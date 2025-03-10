package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.steeplesoft.giftbook.database.model.GiftIdea

@Dao
interface GiftIdeaDao {
    @Query("SELECT * from GiftIdea")
    suspend fun getAll(): List<GiftIdea>

    @Insert
    @Transaction
    suspend fun insertAll(vararg idea: GiftIdea)

    @Query("SELECT g.* FROM giftidea g WHERE g.recipientId = :recipId AND g.occasionId IS NULL")
    suspend fun getCurrentGiftIdeasForRecip(recipId: Int): List<GiftIdea>

    @Query("""
        SELECT g.*
        FROM giftidea g
        WHERE g.recipientId = :recipId
          AND (g.occasionId IS NULL OR g.occasionId = :occasionId)
    """)
    suspend fun getCurrentGiftIdeasForRecipAndOccasion(recipId: Long, occasionId: Long): List<GiftIdea>

    @Query("SELECT g.* FROM giftidea g WHERE g.recipientId = :recipId AND g.occasionId IS NOT NULL")
    suspend fun getUsedGiftIdeasForRecip(recipId: Long): List<GiftIdea>
}
