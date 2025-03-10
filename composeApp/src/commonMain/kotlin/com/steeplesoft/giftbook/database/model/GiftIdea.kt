package com.steeplesoft.giftbook.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class GiftIdea(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "notes")
    var notes: String? = null,
    var recipientId: Int,
    var occasionId: Int? = null,
    val estimatedCost: Float,
    val actualCost: Float? = null,
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
