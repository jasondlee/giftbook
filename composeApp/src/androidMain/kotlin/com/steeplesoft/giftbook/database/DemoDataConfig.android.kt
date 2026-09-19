package com.steeplesoft.giftbook.database

import android.content.pm.ApplicationInfo
import com.steeplesoft.giftbook.AppContext

actual val loadDemoDataOnStartup: Boolean
    get() = (AppContext.get().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
