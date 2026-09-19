package com.steeplesoft.giftbook.form

fun parseNonNegativeInt(value: String): Int? =
    value.toIntOrNull()?.takeIf { it >= 0 }
