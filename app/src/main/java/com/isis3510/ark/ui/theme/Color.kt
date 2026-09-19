package com.isis3510.ark.ui.theme

import androidx.compose.ui.graphics.Color

// Colour palette from the wiki (MS6, section 2.3). Do not add new colours here
// without updating the wiki first.
val ArkPrimary = Color(0xFFF8840E)
val ArkSecondary = Color(0xFF2BA79B)
val ArkAccent = Color(0xFFEB5249)
val ArkBackground = Color(0xFFF9DEAA)
val ArkText = Color(0xFF221100)
val ArkSuccess = Color(0xFFC5FAA8)
val ArkWarning = Color(0xFFFFF170)
val ArkError = Color(0xFFB31212)

// Light cards and rows: the background colour with a 8% layer of the text colour on top,
// so they separate from the screen without adding a new colour to the palette.
val ArkSurfaceDim = Color(0xFFE8CE9C)

// Secondary text: text colour at 70% opacity.
val ArkTextMuted = ArkText.copy(alpha = 0.7f)
val ArkOnDarkMuted = ArkBackground.copy(alpha = 0.7f)
