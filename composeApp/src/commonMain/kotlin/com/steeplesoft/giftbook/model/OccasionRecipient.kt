package com.steeplesoft.giftbook.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.serialization.Serializable

// https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542
// https://stackoverflow.com/questions/48640803/android-room-relation-many-to-many
// https://developer.android.com/training/data-storage/room/relationships#many-to-many

@Entity(
    primaryKeys = ["occasionId", "recipientId"],
    foreignKeys = [
        ForeignKey(entity = Occasion::class, parentColumns = ["id"], childColumns = ["occasionId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Recipient::class, parentColumns = ["id"], childColumns = ["recipientId"], onDelete = ForeignKey.CASCADE)
    ],
    indices =[
        Index(value = ["recipientId"]),
        Index(value = ["occasionId"]),
    ]
)
@Serializable
data class OccasionRecipient (
    val occasionId: Long,
    val recipientId: Long,
    val targetCount: Int,
    val targetCost: Int
)
