package com.example.lifemap.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lifemap.presentation.CreateTask.view.CreateTaskScreen
import com.example.lifemap.presentation.EditTask.view.EditTaskScreen
import com.example.lifemap.presentation.ForgotPassword.view.ForgotPasswordScreen
import com.example.lifemap.presentation.Habits.view.HabitsScreen
import com.example.lifemap.presentation.Home.view.HomeScreen
import com.example.lifemap.presentation.Login.view.LoginScreen
import com.example.lifemap.presentation.Progress.view.ProgressScreen
import com.example.lifemap.presentation.Settings.view.SettingsScreen
import com.example.lifemap.presentation.SignUp.view.SignUpScreen
import com.example.lifemap.presentation.welcome.WelcomeScreen

@Composable
fun Navigation(){
    val rememberNavController = rememberNavController()
    var startDestination by remember { mutableStateOf(Routes.WELCOME) }

    NavHost(navController = rememberNavController, startDestination = startDestination) {
        composable(Routes.WELCOME) {
            WelcomeScreen(rememberNavController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(rememberNavController)
        }
        composable(Routes.SIGNUP) {
            SignUpScreen(rememberNavController)
        }
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(rememberNavController)
        }
        composable(Routes.HOME) {
            HomeScreen(rememberNavController)
        }
        composable(Routes.CREATE_TASK) {
            CreateTaskScreen(rememberNavController)
        }
        composable(
            route = Routes.EDIT_TASK_ROUTE,
            arguments = listOf(
                navArgument(Routes.EDIT_TASK_ARG_TASK_ID) { type = NavType.StringType }
            )
        ) {
            EditTaskScreen(rememberNavController)
        }
        composable(Routes.HABITS) {
            HabitsScreen(rememberNavController)
        }
        composable(Routes.PROGRESS) {
            ProgressScreen(rememberNavController)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(rememberNavController)
        }
    }
}

/**
 * Navigates to one of the bottom-nav tabs (Home, Habits, Progress, Settings)
 * while keeping a single, clean back stack - the same tab isn't stacked
 * multiple times, and switching tabs back and forth restores their state.
 */
fun NavController.navigateToBottomNavRoute(route: String) {
    navigate(route) {
        popUpTo(Routes.HOME) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
