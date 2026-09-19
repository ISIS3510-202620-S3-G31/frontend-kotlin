package com.isis3510.ark.ui.screens.achievementjar

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun AchievementJarScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.AchievementJar.title, onBack = onBack)
}
