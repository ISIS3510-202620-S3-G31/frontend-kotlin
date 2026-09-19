package com.isis3510.ark.ui.screens.bodymapping

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun BodyMappingScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.BodyMapping.title, onBack = onBack)
}
