package com.isis3510.ark.ui.screens.toolhub

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.isis3510.ark.R
import com.isis3510.ark.ui.navigation.ArkDestination

enum class ToolFilter(@StringRes val label: Int) {
    All(R.string.tool_filter_all),
    CalmDown(R.string.tool_filter_calm_down),
    Release(R.string.tool_filter_release),
    Reflect(R.string.tool_filter_reflect),
    Celebrate(R.string.tool_filter_celebrate),
}

data class ToolItem(
    val destination: ArkDestination,
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
    val filter: ToolFilter,
)

// fixed data for now
data class ToolHubUiState(
    val selectedFilter: ToolFilter = ToolFilter.All,
    val tools: List<ToolItem> = allTools,
) {
    val visibleTools: List<ToolItem>
        get() = if (selectedFilter == ToolFilter.All) tools else tools.filter { it.filter == selectedFilter }
}

val allTools = listOf(
    ToolItem(ArkDestination.BlowItOut, R.string.tool_blow_it_out_description, R.drawable.ic_tool_blow, ToolFilter.CalmDown),
    ToolItem(ArkDestination.PhotoOfTheDay, R.string.tool_photo_of_the_day_description, R.drawable.ic_tool_photo, ToolFilter.Celebrate),
    ToolItem(ArkDestination.CustomBreathing, R.string.tool_custom_breathing_description, R.drawable.ic_tool_breathing, ToolFilter.CalmDown),
    ToolItem(ArkDestination.AchievementJar, R.string.tool_achievement_jar_description, R.drawable.ic_tool_jar, ToolFilter.Celebrate),
    ToolItem(ArkDestination.BodyMapping, R.string.tool_body_mapping_description, R.drawable.ic_tool_body, ToolFilter.Reflect),
    ToolItem(ArkDestination.ScreamTank, R.string.tool_scream_tank_description, R.drawable.ic_tool_scream, ToolFilter.Release),
    ToolItem(ArkDestination.ContainmentSection, R.string.tool_containment_section_description, R.drawable.ic_tool_contain, ToolFilter.CalmDown),
    ToolItem(ArkDestination.TearCollection, R.string.tool_tear_collection_description, R.drawable.ic_tool_tear, ToolFilter.Release),
    ToolItem(ArkDestination.EmotionDetective, R.string.tool_emotion_detective_description, R.drawable.ic_tool_detective, ToolFilter.Reflect),
)
