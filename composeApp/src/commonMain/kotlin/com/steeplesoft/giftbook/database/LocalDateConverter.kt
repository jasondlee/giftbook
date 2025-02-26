package com.steeplesoft.giftbook.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

class LocalDateConverter {
    @TypeConverter
    fun toLocalDate(days: String): LocalDate {
        return LocalDate.parse(days)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(LocalDate.Formats.ISO)
    }
}
