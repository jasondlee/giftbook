package com.steeplesoft.giftbook

import androidx.room.Room
import androidx.room.RoomDatabase
import com.steeplesoft.giftbook.database.AppDatabase
import com.steeplesoft.giftbook.database.dbFileName
import org.koin.dsl.module

actual val platformModule = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        val context = AppContext.get()
        Room.databaseBuilder<AppDatabase>(
            context, context.getDatabasePath(dbFileName).absolutePath
        )
    }
}
