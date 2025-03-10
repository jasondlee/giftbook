package com.steeplesoft.giftbook.logger

import com.steeplesoft.giftbook.TAG
import platform.Foundation.NSLog

actual object AppLogger {
    actual fun e(message: String, throwable: Throwable?) {
        if (throwable != null) {
            NSLog("ERROR: [$TAG] $message. Throwable: $throwable CAUSE ${throwable.cause}")
        } else {
            NSLog("ERROR: [$TAG] $message")
        }
    }

    actual fun d(message: String) {
        NSLog("DEBUG: [$TAG] $message")
    }

    actual fun i(message: String) {
        NSLog("INFO: [$TAG] $message")
    }

}
