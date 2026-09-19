package com.isis3510.ark.ui.screens.emotiondetective

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun EmotionDetectiveScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.EmotionDetective.title, onBack = onBack)
}
