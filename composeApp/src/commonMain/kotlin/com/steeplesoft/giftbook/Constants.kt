package com.steeplesoft.giftbook

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal const val TAG = "GIFTBOOK"

fun LocalDate.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
