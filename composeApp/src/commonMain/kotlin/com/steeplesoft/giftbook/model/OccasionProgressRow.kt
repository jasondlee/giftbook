package com.steeplesoft.giftbook.model

data class OccasionProgressRow(
    val recipientId: Long,
    val recipientName: String,
    val occasionId: Long,
    val targetCount: Int,
    val actualCount: Long,
    val targetCost: Int,
    val actualCost: Long,
)

fun OccasionProgressRow.toOccasionProgress() = OccasionProgress(
    recipient = Recipient(id = recipientId, name = recipientName),
    occasionId = occasionId,
    targetCount = targetCount,
    actualCount = actualCount.toInt(),
    targetCost = targetCost,
    actualCost = actualCost.toInt(),
)
