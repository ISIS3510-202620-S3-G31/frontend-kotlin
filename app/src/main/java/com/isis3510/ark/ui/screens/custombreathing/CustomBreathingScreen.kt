package com.isis3510.ark.ui.screens.custombreathing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import com.isis3510.ark.ui.components.ArkCircleButton
import com.isis3510.ark.ui.screens.custombreathing.components.DurationPills
import com.isis3510.ark.ui.screens.custombreathing.components.PatternSelector
import com.isis3510.ark.ui.screens.custombreathing.components.SessionControls
import com.isis3510.ark.ui.screens.custombreathing.components.StepperRow
import com.isis3510.ark.ui.screens.custombreathing.components.TimerCard
import com.isis3510.ark.ui.theme.ArkTextMuted
import com.isis3510.ark.ui.theme.ArkTheme

private val secondsRange = 1..15

@Composable
fun CustomBreathingScreen(onBack: () -> Unit) {
    // TODO: move to the ViewModel, no countdown yet
    var state by remember { mutableStateOf(CustomBreathingUiState()) }

    CustomBreathingContent(
        state = state,
        onBack = onBack,
        onPatternSelected = { pattern ->
            state = if (pattern == BreathingPattern.FourSevenEight) {
                state.copy(pattern = pattern, inhaleSeconds = 4, holdSeconds = 7, exhaleSeconds = 8)
            } else {
                state.copy(pattern = pattern)
            }
        },
        onStepChanged = { step, delta ->
            val seconds = (state.secondsOf(step) + delta).coerceIn(secondsRange)
            state = when (step) {
                BreathingStep.Inhale -> state.copy(inhaleSeconds = seconds)
                BreathingStep.Hold -> state.copy(holdSeconds = seconds)
                BreathingStep.Exhale -> state.copy(exhaleSeconds = seconds)
            }
        },
        onDurationSelected = { state = state.copy(sessionMinutes = it) },
        onReset = {},
        onPause = {},
        onFinish = {},
    )
}

@Composable
private fun CustomBreathingContent(
    state: CustomBreathingUiState,
    onBack: () -> Unit,
    onPatternSelected: (BreathingPattern) -> Unit,
    onStepChanged: (BreathingStep, Int) -> Unit,
    onDurationSelected: (Int) -> Unit,
    onReset: () -> Unit,
    onPause: () -> Unit,
    onFinish: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding(),
    ) {
        Header(onBack = onBack)

        // scrolls on small screens
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            PatternSelector(
                selected = state.pattern,
                onSelect = onPatternSelected,
                modifier = Modifier.padding(vertical = 4.dp),
            )
            TimerCard(state = state, modifier = Modifier.padding(vertical = 8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                state.visibleSteps.forEach { step ->
                    StepperRow(
                        label = stringResource(step.label),
                        seconds = state.secondsOf(step),
                        onDecrease = { onStepChanged(step, -1) },
                        onIncrease = { onStepChanged(step, 1) },
                    )
                }
            }
            DurationPills(
                selectedMinutes = state.sessionMinutes,
                onSelect = onDurationSelected,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        SessionControls(
            onReset = onReset,
            onPause = onPause,
            onFinish = onFinish,
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 8.dp, bottom = 24.dp),
        )
    }
}

@Composable
private fun Header(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArkCircleButton(
            icon = R.drawable.ic_chevron_left,
            contentDescription = stringResource(R.string.breathing_back),
            onClick = onBack,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.breathing_title),
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(R.string.breathing_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = ArkTextMuted,
            )
        }
    }
}

@Preview(widthDp = 390, heightDp = 844)
@Composable
private fun CustomBreathingPreview() {
    ArkTheme {
        CustomBreathingContent(
            state = CustomBreathingUiState(),
            onBack = {},
            onPatternSelected = {},
            onStepChanged = { _, _ -> },
            onDurationSelected = {},
            onReset = {},
            onPause = {},
            onFinish = {},
        )
    }
}
