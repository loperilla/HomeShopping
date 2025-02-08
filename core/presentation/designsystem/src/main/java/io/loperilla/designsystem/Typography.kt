package io.loperilla.designsystem

/*****
 * Project: HomeShopping
 * From: io.loperilla.designsystem
 * Created By Manuel Lopera on 2/2/25 at 16:12
 * All rights reserved 2025
 */

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.loperilla.presentation.designsystem.R

val myFontFamily = FontFamily(
    Font(R.font.regular, FontWeight.Normal),
    Font(R.font.medium, FontWeight.Medium),
    Font(R.font.bold, FontWeight.Bold),
)

val TextSmallSize = 14.sp
val TextBodySize = 18.sp
val TextTitleSize = 22.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextBodySize,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TextTitleSize,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = TextSmallSize,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)