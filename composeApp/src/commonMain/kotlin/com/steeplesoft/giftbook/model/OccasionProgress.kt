package com.steeplesoft.giftbook.model

import kotlinx.serialization.Serializable

@Serializable
data class OccasionProgress(
    val recipient: Recipient,
    val occasionId: Long,
    val targetCount: Int,
    val actualCount: Int,
    val targetCost: Int,
    val actualCost: Int
)
