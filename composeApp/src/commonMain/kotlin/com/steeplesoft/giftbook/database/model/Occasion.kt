package com.steeplesoft.giftbook.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.steeplesoft.giftbook.database.LocalDateConverter
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Occasion (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,
    @field:TypeConverters(LocalDateConverter::class)
    @ColumnInfo(name="eventDate", typeAffinity = ColumnInfo.TEXT)
    var eventDate: LocalDate
)
