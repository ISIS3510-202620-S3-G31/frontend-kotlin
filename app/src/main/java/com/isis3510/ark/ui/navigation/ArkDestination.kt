package com.isis3510.ark.ui.navigation

// Every screen of the app and its route. Navigation is flat (MS6, section 7):
// level 1 are the three bottom tabs, level 2 are the tools opened from the tool hub.
enum class ArkDestination(val route: String, val title: String) {
    // Level 1: bottom tabs
    ToolHub("tool_hub", "Your toolbox"),
    Random("random", "Leave it to chance"),
    Stats("stats", "Stats"),

    // Level 2: tools
    BlowItOut("blow_it_out", "Blow it out"),
    PhotoOfTheDay("photo_of_the_day", "Photo of the day"),
    CustomBreathing("custom_breathing", "Custom breathing"),
    AchievementJar("achievement_jar", "Achievement jar"),
    BodyMapping("body_mapping", "Body mapping"),
    ScreamTank("scream_tank", "Scream tank"),
    ContainmentSection("containment_section", "Containment section"),
    TearCollection("tear_collection", "Tear collection"),
    EmotionDetective("emotion_detective", "Emotion detective"),
}
