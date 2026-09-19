package com.isis3510.ark.ui.screens.stats

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isis3510.ark.ui.components.ArkBottomBar
import com.isis3510.ark.ui.components.PlaceholderScreen
import com.isis3510.ark.ui.navigation.ArkDestination

@Composable
fun StatsScreen(
    onOpen: (ArkDestination) -> Unit,
    onTabSelected: (ArkDestination) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { ArkBottomBar(current = ArkDestination.Stats, onTabSelected = onTabSelected) },
    ) { innerPadding ->
        PlaceholderScreen(title = ArkDestination.Stats.title, modifier = Modifier.padding(innerPadding))
    }
}
