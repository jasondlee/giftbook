package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipient
import com.steeplesoft.giftbook.database.model.OccasionWithRecipients
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
    @Query("SELECT * from Occasion where eventDate >= :limit")
    suspend fun getFutureOccasions(limit: String = LocalDate.now().format(LocalDate.Formats.ISO)): List<Occasion>

    @Insert
    @Transaction
    suspend fun insert(occasion: Occasion) : Long

    @Update
    @Transaction
    suspend fun update(occasion: Occasion)

    @Delete
    @Transaction
    suspend fun delete(occasion: Occasion)

    @Query("""SELECT o.*, r.*
        FROM Occasion o LEFT OUTER JOIN OccasionRecipient occRecip on o.id = occRecip.occasionId,
             Recipient r
        WHERE o.id = :id and occRecip.recipientId = r.id""")
    suspend fun getOccasionRecipients(id: Long): OccasionWithRecipients

    @Query("SELECT * FROM Occasion where eventDate >= :limit")
    suspend fun getOccasionsAndRecipients(limit: String = LocalDate.now().format(LocalDate.Formats.ISO)): List<OccasionWithRecipients>
}
