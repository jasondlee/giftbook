package com.steeplesoft.giftbook.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Entity(
    indices = [
        Index(value = ["recipientId"]),
        Index(value = ["occasionId"]),
    ],
    foreignKeys = [
        ForeignKey(
            entity = Recipient::class,
            parentColumns = ["id"],
            childColumns = ["recipientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Serializable
data class GiftIdea(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "notes")
    var notes: String? = null,
    var recipientId: Long,
    var occasionId: Long? = null,
    var estimatedCost: Int,
    @ColumnInfo
    var actualCost: Int? = null,
)

data class RecipientsWithIdeas(
    @Embedded
    val recipient: Recipient,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipientId"
    )
    val gifts: List<GiftIdea>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val occasion: Occasion?
)
