package com.steeplesoft.giftbook.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

// https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542
// https://stackoverflow.com/questions/48640803/android-room-relation-many-to-many
// https://developer.android.com/training/data-storage/room/relationships#many-to-many

@Entity(
    primaryKeys = ["occasionId", "recipientId"],
    foreignKeys = [
        ForeignKey(entity = Occasion::class, parentColumns = ["id"], childColumns = ["occasionId"]),
        ForeignKey(entity = Recipient::class, parentColumns = ["id"], childColumns = ["recipientId"])
    ],
    indices =[
        Index(value = ["recipientId"]),
        Index(value = ["occasionId"]),
    ]
)
data class OccasionRecipient (
    val occasionId: Long,
    val recipientId: Long,
    val targetCount: Int,
    val targetCost: Int
)
