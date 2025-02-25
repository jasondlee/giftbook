package com.steeplesoft.giftbook.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

// https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542
// https://stackoverflow.com/questions/48640803/android-room-relation-many-to-many
// https://developer.android.com/training/data-storage/room/relationships#many-to-many

@Entity(
    primaryKeys = ["occasionId", "recipientId"],
//    foreignKeys = [
//        ForeignKey(
//            entity = Occasion::class,
//            parentColumns = ["id"],
//            childColumns = ["occasionId"]
//        ),
//        ForeignKey(
//            entity = Recipient::class,
//            parentColumns = ["id"],
//            childColumns = ["recipientId"]
//        )
//    ]
)
data class OccasionRecipientCrossRef (
    var occasionId: Int,
    var recipientId: Int
)


data class OccasionWithRecipients(
    @Embedded
    val occasion: Occasion,

    @Relation(
        parentColumn = "id",
        entity = Recipient::class,
        entityColumn = "id",
        associateBy = Junction(
            value = OccasionRecipientCrossRef::class,
            parentColumn = "occasionId",
            entityColumn = "recipientId"
        )
    )
    val recipient: List<Recipient>
)
