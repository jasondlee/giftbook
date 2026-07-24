package com.steeplesoft.giftbook.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Standard spacing values used throughout the application.
 * Use these constants to maintain consistent spacing.
 */
object Spacing {
    /** Screen-level padding (outer container padding) */
    val screenPadding: Dp = 10.dp
    
    /** Internal component padding (between elements) */
    val internalPadding: Dp = 5.dp
    
    /** Standard vertical spacing between form fields */
    val fieldSpacing: Dp = 16.dp
    
    /** Padding for button groups */
    val buttonPadding: Dp = 3.dp
    
    /** Bottom padding for list items */
    val listItemBottomPadding: Dp = 10.dp
    
    /** Padding around card content */
    val cardPadding: Dp = 15.dp
    
    /** Horizontal padding for dividers */
    val dividerHorizontalPadding: Dp = 5.dp
    
    /** Vertical padding for dividers */
    val dividerVerticalPadding: Dp = 5.dp
}

/**
 * Standard typography sizes used throughout the application.
 * Use these constants to maintain consistent text sizing.
 */
object Typography {
    /** Main screen headers and titles */
    val headerSize: TextUnit = 30.sp
    
    /** Primary body text and list items */
    val primaryTextSize: TextUnit = 24.sp
    
    /** Secondary text, subtitles, and metadata */
    val secondaryTextSize: TextUnit = 18.sp
    
    /** Small text for labels and hints */
    val smallTextSize: TextUnit = 10.sp
}

/**
 * Standard icon sizes used throughout the application.
 */
object IconSize {
    /** Navigation bar icons */
    val navigationIcon: Dp = 36.dp
    
    /** Standard list item icons */
    val listIcon: Dp = 48.dp
}
