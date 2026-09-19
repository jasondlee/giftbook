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
    val title: String,

    @ColumnInfo(name = "notes")
    val notes: String? = null,
    
    val recipientId: Long,
    var occasionId: Long? = null,
    val estimatedCost: Int,
    
    @ColumnInfo
    var actualCost: Int? = null,
)

fun actualGiftCount(ideas: List<GiftIdea>): Int = ideas.count { it.occasionId != null }

fun actualGiftCost(ideas: List<GiftIdea>): Int = ideas.sumOf { it.actualCost ?: 0 }

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
