package com.example.lifemap.presentation.Settings.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.common.components.PlaceholderScreen

/**
 * Placeholder for the Settings tab. Reachable from the bottom nav bar.
 * TODO: replace with the real Settings feature (profile, preferences, logout, ...).
 */
@Composable
fun SettingsScreen(navController: NavController) {
    PlaceholderScreen(
        title = Routes.SETTINGS,
        navController = navController,
        icon = Icons.Filled.Settings
    )
}
