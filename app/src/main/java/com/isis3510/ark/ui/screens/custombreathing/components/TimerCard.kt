package com.isis3510.ark.ui.screens.custombreathing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isis3510.ark.R
import com.isis3510.ark.ui.screens.custombreathing.CustomBreathingUiState
import com.isis3510.ark.ui.theme.ArkOnDarkMuted

// Dark card with the state of the session. Bloom uses the Mindful pose,
// the one MS6 (section 5.5) assigns to the breathing exercises.
@Composable
fun TimerCard(state: CustomBreathingUiState, modifier: Modifier = Modifier) {
    val onDark = MaterialTheme.colorScheme.inverseOnSurface
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(312.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.inverseSurface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BreathingRing(progress = state.progress) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // The countdown is the only text bigger than H1. It stays in Figtree
                    // because Sorean is only for titles.
                    Text(
                        text = state.secondsLeft.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 64.sp, lineHeight = 64.sp),
                        color = onDark,
                    )
                    Text(
                        text = stringResource(state.phase.label),
                        style = MaterialTheme.typography.titleMedium,
                        color = onDark,
                    )
                }
            }
            Text(
                text = stringResource(R.string.breathing_cycle, state.cycle, state.totalCycles),
                style = MaterialTheme.typography.bodyMedium,
                color = ArkOnDarkMuted,
            )
        }

        StatusChip(
            isRunning = state.isRunning,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
        )
        Image(
            painter = painterResource(R.drawable.bloom_mindful),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp)
                .size(width = 62.dp, height = 70.dp),
        )
    }
}

@Composable
private fun StatusChip(isRunning: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, CircleShape)
            .padding(start = 12.dp, end = 14.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // The dot is teal while running and dimmed while paused, and the label changes too.
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = if (isRunning) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline,
                    shape = CircleShape,
                ),
        )
        Text(
            text = stringResource(if (isRunning) R.string.breathing_status_running else R.string.breathing_status_paused),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
