@file:OptIn(ExperimentalForeignApi::class)

package com.steeplesoft.giftbook

import androidx.room.Room
import androidx.room.RoomDatabase
import com.steeplesoft.giftbook.database.AppDatabase
import com.steeplesoft.giftbook.database.dbFileName
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val platformModule = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        val documentDirectoryUrl = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        val documentDirectory = requireNotNull(documentDirectoryUrl?.path)

        Room.databaseBuilder<AppDatabase>(
            name = "$documentDirectory/$dbFileName",
        )
    }
}
