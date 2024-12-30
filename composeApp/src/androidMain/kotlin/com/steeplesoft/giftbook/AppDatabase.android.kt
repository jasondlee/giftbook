package com.steeplesoft.giftbook

import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = AppContext.get()
    return Room.databaseBuilder<AppDatabase>(context,
        context.getDatabasePath(dbFileName).absolutePath)
}
