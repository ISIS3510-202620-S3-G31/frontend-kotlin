package com.isis3510.ark.ui.screens.photooftheday

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun PhotoOfTheDayScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.PhotoOfTheDay.title, onBack = onBack)
}
