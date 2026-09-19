package com.isis3510.ark.ui.screens.tearcollection

import androidx.compose.ui.graphics.Color
import com.isis3510.ark.ui.theme.ArkAccent
import com.isis3510.ark.ui.theme.ArkPrimary
import com.isis3510.ark.ui.theme.ArkSecondary

data class TearItem(
    val id: String,
    val reason: String,
    val rootEmotion: String,
    val reliefLevel: Int,
    val reflection: String,
    val dateFormatted: String,
    val timeFormatted: String,
    val colorTone: Color
)

enum class TearViewStep {
    SANCTUARY_LIST,
    TEAR_DETAIL,
    LOG_NEW_TEAR
}

data class TearCollectionState(
    val currentStep: TearViewStep = TearViewStep.SANCTUARY_LIST,
    val tears: List<TearItem> = emptyList(),
    val selectedTear: TearItem? = null,
    val isSavedToInsights: Boolean = false
)
