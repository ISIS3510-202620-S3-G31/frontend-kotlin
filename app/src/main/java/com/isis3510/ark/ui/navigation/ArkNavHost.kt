package com.isis3510.ark.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isis3510.ark.ui.screens.achievementjar.AchievementJarScreen
import com.isis3510.ark.ui.screens.blowitout.BlowItOutScreen
import com.isis3510.ark.ui.screens.bodymapping.BodyMappingScreen
import com.isis3510.ark.ui.screens.containmentsection.ContainmentSectionScreen
import com.isis3510.ark.ui.screens.custombreathing.CustomBreathingScreen
import com.isis3510.ark.ui.screens.emotiondetective.EmotionDetectiveScreen
import com.isis3510.ark.ui.screens.photooftheday.PhotoOfTheDayScreen
import com.isis3510.ark.ui.screens.random.RandomScreen
import com.isis3510.ark.ui.screens.screamtank.ScreamTankScreen
import com.isis3510.ark.ui.screens.stats.StatsScreen
import com.isis3510.ark.ui.screens.tearcollection.TearCollectionScreen
import com.isis3510.ark.ui.screens.toolhub.ToolHubScreen

// Single navigation graph of the app. Each screen lives in its own package under
// ui/screens, so to work on a view you only need to touch the files of that package.
@Composable
fun ArkNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val onBack: () -> Unit = { navController.popBackStack() }
    val onOpen: (ArkDestination) -> Unit = { destination ->
        navController.navigate(destination.route) { launchSingleTop = true }
    }

    NavHost(
        navController = navController,
        startDestination = ArkDestination.ToolHub.route,
        modifier = modifier,
    ) {
        composable(ArkDestination.ToolHub.route) { ToolHubScreen(onOpen = onOpen) }
        composable(ArkDestination.Random.route) { RandomScreen(onOpen = onOpen) }
        composable(ArkDestination.Stats.route) { StatsScreen(onOpen = onOpen) }

        composable(ArkDestination.BlowItOut.route) { BlowItOutScreen(onBack = onBack) }
        composable(ArkDestination.PhotoOfTheDay.route) { PhotoOfTheDayScreen(onBack = onBack) }
        composable(ArkDestination.CustomBreathing.route) { CustomBreathingScreen(onBack = onBack) }
        composable(ArkDestination.AchievementJar.route) { AchievementJarScreen(onBack = onBack) }
        composable(ArkDestination.BodyMapping.route) { BodyMappingScreen(onBack = onBack) }
        composable(ArkDestination.ScreamTank.route) { ScreamTankScreen(onBack = onBack) }
        composable(ArkDestination.ContainmentSection.route) { ContainmentSectionScreen(onBack = onBack) }
        composable(ArkDestination.TearCollection.route) { TearCollectionScreen(onBack = onBack) }
        composable(ArkDestination.EmotionDetective.route) { EmotionDetectiveScreen(onBack = onBack) }
    }
}
