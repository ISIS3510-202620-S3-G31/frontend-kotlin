package com.isis3510.ark.ui.screens.random

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun RandomScreen(onOpen: (ArkDestination) -> Unit) {
    PlaceholderScreen(title = ArkDestination.Random.title)
}
