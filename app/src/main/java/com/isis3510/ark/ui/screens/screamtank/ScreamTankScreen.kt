package com.isis3510.ark.ui.screens.screamtank

import androidx.compose.runtime.Composable
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun ScreamTankScreen(onBack: () -> Unit) {
    PlaceholderScreen(title = ArkDestination.ScreamTank.title, onBack = onBack)
}
