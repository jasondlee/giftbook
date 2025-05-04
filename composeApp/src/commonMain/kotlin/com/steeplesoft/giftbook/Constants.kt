package com.steeplesoft.giftbook

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal const val TAG = "GIFTBOOK"

fun LocalDate.Companion.now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

/*
fun Int.toDollars() : String {
    val a = this

    val left = this.toInt()
    val right = (this - left) * 100

    val dollars = a - a.toInt()
    val fp = dollars * 10f.pow(dollars.toString().length - 2)
    val cents = "${right.toInt()}00".substring(0,2)

    return "${left}.$cents"
}
*/
