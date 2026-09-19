package com.steeplesoft.giftbook

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.giftbook.database.AppDatabase
import com.steeplesoft.giftbook.database.dao.GiftIdeaDao
import com.steeplesoft.giftbook.database.dao.OccasionDao
import com.steeplesoft.giftbook.database.dao.RecipientDao
import com.steeplesoft.giftbook.database.loadDemoData
import com.steeplesoft.giftbook.database.MIGRATION_1_2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// https://medium.com/@yeldar.nurpeissov/master-kotlin-multiplatform-navigation-with-decompose-add-di-with-kodein-and-koin-405462b2691b
// https://gist.github.com/aartikov/30e182fd58ed9697af498bb22ef4edfa
// https://medium.com/arconsis/dependency-injection-using-koin-in-kotlin-multiplatform-mobile-kmm-eb4cfe82ed6
// https://medium.com/@j.c.moreirapinto/simplifying-cross-platform-app-development-dependency-injection-with-koin-in-compose-multiplatform-f77595396fbc
// https://proandroiddev.com/how-to-use-koin-scopes-with-decompose-components-14d04ea18e1a

expect val platformModule : Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        modules(appModule, platformModule)
        config?.invoke(this)
    }
}

val appModule = module {
    single<StackNavigation<NavigationConfig>> { StackNavigation() }
    single<AppDatabase>(createdAtStart = true) {
        get<RoomDatabase.Builder<AppDatabase>>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .addMigrations(MIGRATION_1_2)
            .build()
            .also { database ->
                if (loadDemoDataOnStartup) {
                    runBlocking(Dispatchers.IO) { loadDemoData(database) }
                }
            }
    }
    single<GiftIdeaDao> { get<AppDatabase>().giftIdeaDao() }
    single<OccasionDao> { get<AppDatabase>().occasionDao() }
    single<RecipientDao> { get<AppDatabase>().recipientDao() }
}
