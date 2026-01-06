package com.steeplesoft.giftbook.database

import androidx.room.TypeConverter
import com.steeplesoft.giftbook.model.EventType

class EventTypeConverter {
    @TypeConverter
    fun toEventType(type: Int): EventType {
        return EventType.Companion.of(type)
    }

    @TypeConverter
    fun fromEventType(type: EventType): Int {
        return type.code
    }
}
