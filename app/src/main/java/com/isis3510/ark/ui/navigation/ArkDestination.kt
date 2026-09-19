package com.isis3510.ark.ui.navigation

enum class ArkDestination(val route: String, val title: String) {
    // bottom tabs
    ToolHub("tool_hub", "Your toolbox"),
    Random("random", "Leave it to chance"),
    Stats("stats", "Stats"),

    // tools
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
