package com.steeplesoft.giftbook

import android.app.Application

class GiftBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppContext.apply { set(applicationContext) }

/*
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MyApplication)

            modules(appModule, provideRepositoryModule, provideDataSourceModule)
        }
*/
    }
}
