package com.isis3510.ark.ui.theme

import androidx.compose.ui.graphics.Color

// MS6 palette
val ArkPrimary = Color(0xFFF8840E)
val ArkSecondary = Color(0xFF2BA79B)
val ArkAccent = Color(0xFFEB5249)
val ArkBackground = Color(0xFFF9DEAA)
val ArkText = Color(0xFF221100)
val ArkSuccess = Color(0xFFC5FAA8)
val ArkWarning = Color(0xFFFFF170)
val ArkError = Color(0xFFB31212)

// background + 8% of the text color, for cards and rows
val ArkSurfaceDim = Color(0xFFE8CE9C)

// secondary text (70%)
val ArkTextMuted = ArkText.copy(alpha = 0.7f)
val ArkOnDarkMuted = ArkBackground.copy(alpha = 0.7f)
