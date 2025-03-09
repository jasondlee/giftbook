package com.steeplesoft.giftbook

import android.content.Context
import java.lang.ref.WeakReference

actual object AppContext {
    private var value: WeakReference<Context?>? = null
    fun set(context: Context) {
        value = WeakReference(context)
    }
    internal fun get(): Context {
        return value?.get() ?: throw RuntimeException("Context Error")
    }
}
