package com.isis3510.ark.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.isis3510.ark.R

// Sorean only for titles, Figtree for the rest
val Sorean = FontFamily(Font(R.font.sorean_bold, FontWeight.Normal))
val Figtree = FontFamily(Font(R.font.figtree_regular, FontWeight.Normal))

private val H1 = TextStyle(fontFamily = Sorean, fontWeight = FontWeight.Normal, fontSize = 28.sp, lineHeight = 34.sp)
private val H2 = TextStyle(fontFamily = Figtree, fontWeight = FontWeight.Normal, fontSize = 22.sp, lineHeight = 29.sp)
private val H3 = TextStyle(fontFamily = Figtree, fontWeight = FontWeight.Normal, fontSize = 18.sp, lineHeight = 23.sp)
private val Body = TextStyle(fontFamily = Figtree, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp)

// H1 -> headlineLarge, H2 -> titleLarge, H3 -> titleMedium, Body -> bodyMedium
val ArkTypography = Typography(
    displayLarge = H1,
    displayMedium = H1,
    displaySmall = H1,
    headlineLarge = H1,
    headlineMedium = H1,
    headlineSmall = H2,
    titleLarge = H2,
    titleMedium = H3,
    titleSmall = H3,
    bodyLarge = Body,
    bodyMedium = Body,
    bodySmall = Body,
    labelLarge = Body,
    labelMedium = Body,
    labelSmall = Body,
)
