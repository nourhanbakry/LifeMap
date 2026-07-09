package com.example.lifemap.presentation.Progress.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.common.components.PlaceholderScreen

/**
 * Placeholder for the Progress tab. Reachable from the bottom nav bar.
 * TODO: replace with the real Progress feature (charts, stats, streak history, ...).
 */
@Composable
fun ProgressScreen(navController: NavController) {
    PlaceholderScreen(
        title = Routes.PROGRESS,
        navController = navController,
        icon = Icons.Filled.BarChart
    )
}
