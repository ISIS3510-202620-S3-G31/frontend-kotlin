package com.isis3510.ark.ui.screens.toolhub

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import com.isis3510.ark.ui.components.ArkBottomBar
import com.isis3510.ark.ui.components.ArkCircleButton
import com.isis3510.ark.ui.navigation.ArkDestination
import com.isis3510.ark.ui.screens.toolhub.components.ChanceCard
import com.isis3510.ark.ui.screens.toolhub.components.FilterPills
import com.isis3510.ark.ui.screens.toolhub.components.ToolCard
import com.isis3510.ark.ui.theme.ArkTextMuted
import com.isis3510.ark.ui.theme.ArkTheme

// Home of the app (level 1). It has no back button and it is one of the three
// screens that show the bottom tab bar.
@Composable
fun ToolHubScreen(
    onOpen: (ArkDestination) -> Unit,
    onTabSelected: (ArkDestination) -> Unit,
) {
    // Only the view for now: the selected filter lives here until the ViewModel exists.
    var selectedFilter by rememberSaveable { mutableStateOf(ToolFilter.All) }

    ToolHubContent(
        state = ToolHubUiState(selectedFilter = selectedFilter),
        onFilterSelected = { selectedFilter = it },
        onOpen = onOpen,
        onTabSelected = onTabSelected,
        onProfile = {},
    )
}

@Composable
private fun ToolHubContent(
    state: ToolHubUiState,
    onFilterSelected: (ToolFilter) -> Unit,
    onOpen: (ArkDestination) -> Unit,
    onTabSelected: (ArkDestination) -> Unit,
    onProfile: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { ArkBottomBar(current = ArkDestination.ToolHub, onTabSelected = onTabSelected) },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .statusBarsPadding(),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item { Header(onProfile = onProfile) }
            item {
                FilterPills(
                    selected = state.selectedFilter,
                    onSelect = onFilterSelected,
                    modifier = Modifier.padding(vertical = 2.dp),
                )
            }
            item {
                ChanceCard(
                    onSurpriseMe = { onTabSelected(ArkDestination.Random) },
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 12.dp),
                )
            }
            item { SectionLabel(count = state.visibleTools.size) }
            items(state.visibleTools, key = { it.destination.route }) { tool ->
                ToolCard(
                    tool = tool,
                    onClick = { onOpen(tool.destination) },
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
                )
            }
        }
    }
}

@Composable
private fun Header(onProfile: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = stringResource(R.string.tool_hub_title), style = MaterialTheme.typography.headlineLarge)
            ArkCircleButton(
                icon = R.drawable.ic_profile,
                contentDescription = stringResource(R.string.tool_hub_profile),
                onClick = onProfile,
            )
        }
        Text(
            text = stringResource(R.string.tool_hub_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = ArkTextMuted,
        )
    }
}

@Composable
private fun SectionLabel(count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = stringResource(R.string.tool_hub_all_tools), style = MaterialTheme.typography.titleMedium)
        Text(text = count.toString(), style = MaterialTheme.typography.bodyMedium, color = ArkTextMuted)
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun ToolHubPreview() {
    ArkTheme {
        ToolHubContent(
            state = ToolHubUiState(),
            onFilterSelected = {},
            onOpen = {},
            onTabSelected = {},
            onProfile = {},
        )
    }
}
