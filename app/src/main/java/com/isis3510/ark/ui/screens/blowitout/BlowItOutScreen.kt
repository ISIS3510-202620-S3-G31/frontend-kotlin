package com.isis3510.ark.ui.screens.blowitout

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun BlowItOutScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.BlowItOut.title, onBack = onBack)
}
