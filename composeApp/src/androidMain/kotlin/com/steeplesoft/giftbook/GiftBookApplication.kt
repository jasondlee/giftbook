package com.steeplesoft.giftbook

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class GiftBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppContext.apply { set(applicationContext) }

        initKoin {
            androidLogger()
            androidContext(this@GiftBookApplication)
        }
    }
}
