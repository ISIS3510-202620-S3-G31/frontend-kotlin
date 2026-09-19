package com.isis3510.ark.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import com.isis3510.ark.ui.navigation.ArkDestination
import com.isis3510.ark.ui.theme.ArkTextMuted

private data class BottomTab(
    val destination: ArkDestination,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
)

// The three level 1 sections (MS6, section 7.3). No more than 3 tabs.
private val bottomTabs = listOf(
    BottomTab(ArkDestination.ToolHub, R.string.tab_tools, R.drawable.ic_tools),
    BottomTab(ArkDestination.Random, R.string.tab_random, R.drawable.ic_random),
    BottomTab(ArkDestination.Stats, R.string.tab_stats, R.drawable.ic_stats),
)

@Composable
fun ArkBottomBar(
    current: ArkDestination,
    onTabSelected: (ArkDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .selectableGroup(),
        ) {
            bottomTabs.forEach { tab ->
                val selected = tab.destination == current
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .selectable(
                            selected = selected,
                            role = Role.Tab,
                            onClick = { onTabSelected(tab.destination) },
                        ),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // The active tab is marked with the teal pill and the full colour label,
                    // so the state does not depend only on colour.
                    Box(
                        modifier = Modifier
                            .size(width = 64.dp, height = 32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selected) MaterialTheme.colorScheme.secondary else Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(tab.icon),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    Text(
                        text = stringResource(tab.label),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selected) MaterialTheme.colorScheme.onBackground else ArkTextMuted,
                    )
                }
            }
        }
    }
}
