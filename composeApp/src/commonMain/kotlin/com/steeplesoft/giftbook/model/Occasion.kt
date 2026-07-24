package com.steeplesoft.giftbook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.steeplesoft.giftbook.database.EventTypeConverter
import com.steeplesoft.giftbook.database.LocalDateConverter
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Occasion(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,
    
    @field:TypeConverters(LocalDateConverter::class)
    @ColumnInfo(name = "eventDate", typeAffinity = ColumnInfo.TEXT)
    val eventDate: LocalDate,
    
    @field:TypeConverters(EventTypeConverter::class)
    @ColumnInfo(name = "eventType", typeAffinity = ColumnInfo.INTEGER)
    val eventType: EventType = EventType.OTHER
)
