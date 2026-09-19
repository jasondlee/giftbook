package com.steeplesoft.giftbook.form

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ValidationTest {
    @Test
    fun parsesNonNegativeInteger() {
        assertEquals(42, parseNonNegativeInt("42"))
        assertEquals(0, parseNonNegativeInt("0"))
    }

    @Test
    fun rejectsInvalidOrNegativeInteger() {
        assertNull(parseNonNegativeInt(""))
        assertNull(parseNonNegativeInt(" 12 "))
        assertNull(parseNonNegativeInt("-1"))
        assertNull(parseNonNegativeInt("12.50"))
    }
}
