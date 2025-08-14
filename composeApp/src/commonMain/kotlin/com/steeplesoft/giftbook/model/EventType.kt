package com.steeplesoft.giftbook.model

import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.anniversary
import giftbook.composeapp.generated.resources.cake
import giftbook.composeapp.generated.resources.gift
import giftbook.composeapp.generated.resources.graduation
import giftbook.composeapp.generated.resources.tree
import giftbook.composeapp.generated.resources.valentines
import org.jetbrains.compose.resources.DrawableResource

enum class EventType(
    val code: Int,
    val label: String,
    val image: DrawableResource
) {
    BIRTHDAY(0, "Birthday", Res.drawable.cake),
    CHRISTMAS(1, "Christmas", Res.drawable.tree),
    ANNIVERSARY(2, "Anniversary", Res.drawable.anniversary),
    GRADUATION(3, "Graduation", Res.drawable.graduation),
    VALENTINES(4, "Valentine's Day", Res.drawable.valentines),
    OTHER(999, "Other", Res.drawable.gift);

    companion object {
        fun of(code: Int): EventType {

            return when (code) {
                0 -> BIRTHDAY
                1 -> CHRISTMAS
                2 -> ANNIVERSARY
                3 -> GRADUATION
                4 -> VALENTINES
                999 -> OTHER
                else -> throw RuntimeException("Unknown event type")
            }
        }
    }
}

//                Image(
//                    painter = painterResource(Res.drawable.back),
//                    contentDescription = "Back",
//                    modifier = Modifier.size(48.dp)
//                )
