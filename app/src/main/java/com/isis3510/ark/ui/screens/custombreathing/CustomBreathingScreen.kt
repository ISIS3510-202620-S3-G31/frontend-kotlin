package com.isis3510.ark.ui.screens.custombreathing

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun CustomBreathingScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.CustomBreathing.title, onBack = onBack)
}
