package com.steeplesoft.giftbook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform