package com.isis3510.ark.ui.screens.tearcollection

import androidx.lifecycle.ViewModel
import com.isis3510.ark.ui.theme.ArkAccent
import com.isis3510.ark.ui.theme.ArkPrimary
import com.isis3510.ark.ui.theme.ArkSecondary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TearCollectionViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        TearCollectionState(
            tears = initialTears()
        )
    )
    val state: StateFlow<TearCollectionState> = _state.asStateFlow()

    fun selectTear(tear: TearItem) {
        _state.update {
            it.copy(
                currentStep = TearViewStep.TEAR_DETAIL,
                selectedTear = tear,
                isSavedToInsights = false
            )
        }
    }

    fun openLogNewTear() {
        _state.update { it.copy(currentStep = TearViewStep.LOG_NEW_TEAR) }
    }

    fun backToSanctuary() {
        _state.update {
            it.copy(
                currentStep = TearViewStep.SANCTUARY_LIST,
                selectedTear = null
            )
        }
    }

    fun saveToInsights() {
        _state.update { it.copy(isSavedToInsights = true) }
    }

    fun addTear(reason: String, reliefLevel: Int) {
        val newTear = TearItem(
            id = System.currentTimeMillis().toString(),
            reason = reason,
            rootEmotion = deduceEmotion(reason),
            reliefLevel = reliefLevel,
            reflection = deduceReflection(reason),
            dateFormatted = "Today",
            timeFormatted = "Just now",
            colorTone = if (reliefLevel >= 7) ArkSecondary else ArkPrimary
        )

        _state.update {
            it.copy(
                tears = listOf(newTear) + it.tears,
                currentStep = TearViewStep.SANCTUARY_LIST,
                selectedTear = null
            )
        }
    }

    private fun deduceEmotion(reason: String): String {
        return when {
            reason.contains("deadline", ignoreCase = true) || reason.contains("overwhelm", ignoreCase = true) -> "Overwhelm & Anxiety"
            reason.contains("missing", ignoreCase = true) || reason.contains("grief", ignoreCase = true) -> "Longing & Melancholy"
            reason.contains("conversation", ignoreCase = true) || reason.contains("argument", ignoreCase = true) -> "Emotional Relief & Release"
            else -> "Vulnerability & Self-Care"
        }
    }

    private fun deduceReflection(reason: String): String {
        return "You allowed your nervous system to reset. Drink a glass of water and rest your eyes for a few minutes."
    }

    private fun initialTears(): List<TearItem> {
        return listOf(
            TearItem(
                id = "1",
                reason = "Overwhelm & deadline stress",
                rootEmotion = "Overwhelm & Anxiety",
                reliefLevel = 8,
                reflection = "You allowed your nervous system to reset. Drink a glass of water and rest your eyes for a few minutes.",
                dateFormatted = "Sep 18",
                timeFormatted = "10:42 PM",
                colorTone = ArkSecondary
            ),
            TearItem(
                id = "2",
                reason = "Missing someone deeply",
                rootEmotion = "Grief & Nostalgia",
                reliefLevel = 7,
                reflection = "Honoring your longing is a sign of love. Hold a warm cup and breathe deeply.",
                dateFormatted = "Sep 16",
                timeFormatted = "7:15 PM",
                colorTone = ArkPrimary
            ),
            TearItem(
                id = "3",
                reason = "Relief after a hard conversation",
                rootEmotion = "Emotional Release",
                reliefLevel = 9,
                reflection = "You were brave and honest. Give yourself credit for speaking your truth.",
                dateFormatted = "Sep 12",
                timeFormatted = "11:03 PM",
                colorTone = ArkSecondary
            ),
            TearItem(
                id = "4",
                reason = "Exhaustion after long study night",
                rootEmotion = "Cognitive Burnout",
                reliefLevel = 8,
                reflection = "Rest is not a reward, it is a biological necessity. Go rest comfortably.",
                dateFormatted = "Sep 09",
                timeFormatted = "2:30 AM",
                colorTone = ArkPrimary
            ),
            TearItem(
                id = "5",
                reason = "Tears of unexpected joy & gratitude",
                rootEmotion = "Heartfelt Gratitude",
                reliefLevel = 10,
                reflection = "Cherish this sweet moment of connection and safety in your body.",
                dateFormatted = "Sep 04",
                timeFormatted = "6:20 PM",
                colorTone = ArkAccent
            )
        )
    }
}
