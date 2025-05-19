package com.steeplesoft.giftbook.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.steeplesoft.giftbook.database.LocalDateConverter
import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.anniversary
import giftbook.composeapp.generated.resources.cake
import giftbook.composeapp.generated.resources.gift
import giftbook.composeapp.generated.resources.graduation
import giftbook.composeapp.generated.resources.nativity
import giftbook.composeapp.generated.resources.valentines
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

@Entity
@Serializable
data class Occasion (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var name: String,
    @field:TypeConverters(LocalDateConverter::class)
    @ColumnInfo(name="eventDate", typeAffinity = ColumnInfo.TEXT)
    var eventDate: LocalDate,
    @field:TypeConverters(EventTypeConverter::class)
    @ColumnInfo(name="eventType", typeAffinity = ColumnInfo.INTEGER)
    var eventType: EventType = EventType.OTHER
)

class EventTypeConverter {
    @TypeConverter
    fun toEventType(type: Int): EventType {
        return EventType.of(type)
    }

    @TypeConverter
    fun fromEventType(type: EventType): Int {
        return type.code
    }
}


enum class EventType(val code: Int, val image: DrawableResource) {
    BIRTHDAY(0, Res.drawable.cake),
    CHRISTMAS(1, Res.drawable.nativity),
    ANNIVERSARY(2, Res.drawable.anniversary),
    GRADUATION(3, Res.drawable.graduation),
    VALENTINES(4, Res.drawable.valentines),
    OTHER(999, Res.drawable.gift);

    companion object {
        fun of(code: Int): EventType {
            return when (code) {
                0 -> BIRTHDAY
                1 -> CHRISTMAS
                2 -> ANNIVERSARY
                3 -> GRADUATION
                4 -> VALENTINES
                999 -> OTHER
                else -> throw RuntimeException("Unknown event type")
            }
        }
    }
}
