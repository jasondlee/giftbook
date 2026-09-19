package com.steeplesoft.giftbook.model

import kotlin.test.Test
import kotlin.test.assertEquals

class OccasionProgressTest {
    @Test
    fun calculatesActualGiftCountAndCost() {
        val ideas = listOf(
            GiftIdea(1, "Book", recipientId = 10, occasionId = 20, estimatedCost = 30, actualCost = 25),
            GiftIdea(2, "Mug", recipientId = 10, occasionId = null, estimatedCost = 15),
            GiftIdea(3, "Game", recipientId = 10, occasionId = 20, estimatedCost = 40, actualCost = null),
        )

        assertEquals(2, actualGiftCount(ideas))
        assertEquals(25, actualGiftCost(ideas))
    }
}
