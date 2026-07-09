package com.example.lifemap.presentation.Habits.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.common.components.PlaceholderScreen

/**
 * Placeholder for the Habits tab. Reachable from the bottom nav bar.
 * TODO: replace with the real Habits feature (list, tracking, streaks, ...).
 */
@Composable
fun HabitsScreen(navController: NavController) {
    PlaceholderScreen(
        title = Routes.HABITS,
        navController = navController,
        icon = Icons.Filled.Repeat
    )
}
