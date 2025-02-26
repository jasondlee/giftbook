package com.steeplesoft.giftbook

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val TAG = "GIFTBOOK"

val mainColor = Color(0xFFfbeace) // peach
val secondaryColor = Color(0xFFf5d4b2) // tan

val Teal200 = Color(0xFF03DAC5)
val Navy700 = Color(0xFF000080)
val Navy200 = Color(0xFF5858EB)

val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

// Set of Material typography styles to start with
val typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

fun LocalDate.Companion.now() = Clock.System.now()
    .toLocalDateTime(TimeZone.currentSystemDefault()).date
