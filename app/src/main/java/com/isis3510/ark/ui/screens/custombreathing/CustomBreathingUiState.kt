package com.isis3510.ark.ui.screens.custombreathing

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.isis3510.ark.R

enum class BreathingPattern(@StringRes val label: Int, @DrawableRes val icon: Int, val hasHold: Boolean) {
    TwoStep(R.string.breathing_pattern_two_step, R.drawable.ic_two_step, hasHold = false),
    ThreeStep(R.string.breathing_pattern_three_step, R.drawable.ic_three_step, hasHold = true),
    FourSevenEight(R.string.breathing_pattern_four_seven_eight, R.drawable.ic_sleep_step, hasHold = true),
}

enum class BreathingPhase(@StringRes val label: Int) {
    Inhale(R.string.breathing_phase_inhale),
    Hold(R.string.breathing_phase_hold),
    Exhale(R.string.breathing_phase_exhale),
}

enum class BreathingStep(@StringRes val label: Int) {
    Inhale(R.string.breathing_step_inhale),
    Hold(R.string.breathing_step_hold),
    Exhale(R.string.breathing_step_exhale),
}

val sessionDurations = listOf(2, 5, 10)

// Everything the screen needs to draw itself. The default values are the mid-session
// moment of the mockup. When the ViewModel exists it will expose this same class
// and the countdown will update secondsLeft, phase, cycle and progress.
data class CustomBreathingUiState(
    val pattern: BreathingPattern = BreathingPattern.ThreeStep,
    val inhaleSeconds: Int = 4,
    val holdSeconds: Int = 7,
    val exhaleSeconds: Int = 6,
    val sessionMinutes: Int = 5,
    val isRunning: Boolean = true,
    val phase: BreathingPhase = BreathingPhase.Inhale,
    val secondsLeft: Int = 4,
    val cycle: Int = 2,
    val totalCycles: Int = 6,
    val progress: Float = 0.4f,
) {
    fun secondsOf(step: BreathingStep): Int = when (step) {
        BreathingStep.Inhale -> inhaleSeconds
        BreathingStep.Hold -> holdSeconds
        BreathingStep.Exhale -> exhaleSeconds
    }

    val visibleSteps: List<BreathingStep>
        get() = BreathingStep.entries.filter { it != BreathingStep.Hold || pattern.hasHold }
}
