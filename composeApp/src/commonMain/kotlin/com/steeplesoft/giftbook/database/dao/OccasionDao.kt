package com.steeplesoft.giftbook.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.steeplesoft.giftbook.database.model.Occasion
import com.steeplesoft.giftbook.database.model.OccasionRecipientCrossRef
import com.steeplesoft.giftbook.database.model.OccasionWithRecipients
import com.steeplesoft.giftbook.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@Dao
interface OccasionDao {
    @Transaction
    @Query("SELECT * FROM Occasion")
    fun getAll(): List<Occasion>

    @Transaction
    @Query("SELECT * from Occasion where eventDate >= :limit")
    fun getFutureOccasions(limit : String = LocalDate.now().format(LocalDate.Formats.ISO)): List<Occasion>

    @Insert
    @Transaction
    suspend fun insertAll(vararg meals: Occasion)

    @Query("SELECT * FROM Occasion")
    fun getOccasionRecipients(): List<OccasionWithRecipients>

    @Insert
    @Transaction
    suspend fun addRecipients(vararg recips: OccasionRecipientCrossRef)
}
