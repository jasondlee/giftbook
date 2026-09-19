package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.steeplesoft.giftbook.model.Occasion
import com.steeplesoft.giftbook.model.OccasionRecipient
import com.steeplesoft.giftbook.model.OccasionProgressRow
import com.steeplesoft.giftbook.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@Dao
interface OccasionDao {
    @Transaction
    @Query("SELECT * FROM Occasion")
    suspend fun getAll(): List<Occasion>

    @Query("SELECT * FROM Occasion WHERE id = :occasionId")
    suspend fun getOccasion(occasionId: Long): Occasion

    @Insert
    @Transaction
    suspend fun addRecipients(vararg recips: OccasionRecipient)

    @Transaction
    @Query("SELECT * from Occasion where eventDate >= :limit order by eventDate")
    suspend fun getFutureOccasions(limit: String = LocalDate.now().format(LocalDate.Formats.ISO)): List<Occasion>

    @Query(
        """
        SELECT r.id AS recipientId,
               r.name AS recipientName,
               j.occasionId AS occasionId,
               j.targetCount AS targetCount,
               j.targetCost AS targetCost,
               SUM(CASE WHEN g.occasionId = :occasionId THEN 1 ELSE 0 END) AS actualCount,
               COALESCE(SUM(CASE WHEN g.occasionId = :occasionId THEN COALESCE(g.actualCost, 0) ELSE 0 END), 0) AS actualCost
        FROM OccasionRecipient AS j
        INNER JOIN Recipient AS r ON r.id = j.recipientId
        LEFT JOIN GiftIdea AS g
          ON g.recipientId = j.recipientId
         AND (g.occasionId IS NULL OR g.occasionId = :occasionId)
        WHERE j.occasionId = :occasionId
        GROUP BY r.id, r.name, j.occasionId, j.targetCount, j.targetCost
        ORDER BY r.name
        """
    )
    suspend fun getProgress(occasionId: Long): List<OccasionProgressRow>

    @Insert
    @Transaction
    suspend fun insertOccasionRecip(occasion: OccasionRecipient)

    @Update
    @Transaction
    suspend fun updateOccasionRecip(occasion: OccasionRecipient)

    @Delete
    @Transaction
    suspend fun deleteOccasionRecip(occasion: OccasionRecipient)

    @Insert
    @Transaction
    suspend fun insert(occasion: Occasion) : Long

    @Update
    @Transaction
    suspend fun update(occasion: Occasion)

    @Delete
    @Transaction
    suspend fun delete(occasion: Occasion)

//    @Query("""SELECT o.*, r.*
//        FROM Occasion o LEFT OUTER JOIN OccasionRecipient occRecip on o.id = occRecip.occasionId,
//             Recipient r
//        WHERE o.id = :id and occRecip.recipientId = r.id""")
//    suspend fun getOccasionRecipients(id: Long): OccasionWithRecipients

//    @Query("SELECT * FROM Occasion where eventDate >= :limit")
//    suspend fun getOccasionsAndRecipients(limit: String = LocalDate.now().format(LocalDate.Formats.ISO)): List<OccasionWithRecipients>
}
