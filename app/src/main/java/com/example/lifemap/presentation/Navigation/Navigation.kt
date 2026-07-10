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
import com.example.lifemap.presentation.Habits.view.CreateHabitScreen
import com.example.lifemap.presentation.Habits.view.HabitsScreen
import com.example.lifemap.presentation.Habits.view.UpdateHabitScreen
import com.example.lifemap.presentation.Home.view.HomeScreen
import com.example.lifemap.presentation.Login.view.LoginScreen
import com.example.lifemap.presentation.Progress.view.ProgressScreen
import com.example.lifemap.presentation.Settings.view.SettingsScreen
import com.example.lifemap.presentation.SignUp.view.SignUpScreen
import com.example.lifemap.presentation.welcome.WelcomeScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    var startDestination by remember {
        mutableStateOf(Routes.WELCOME)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.WELCOME) {
            WelcomeScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.SIGNUP) {
            SignUpScreen(navController)
        }

        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController)
        }

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.CREATE_TASK) {
            CreateTaskScreen(navController)
        }

        composable(
            route = Routes.EDIT_TASK_ROUTE,
            arguments = listOf(
                navArgument(Routes.EDIT_TASK_ARG_TASK_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            EditTaskScreen(navController)
        }

        composable(Routes.HABITS) {
            HabitsScreen(
                navController = navController,
                onAddHabit = {
                    navController.navigate(Routes.CREATE_HABIT)
                },
                onHabitClick = { habitId ->
                    navController.navigate(
                        Routes.updateHabitRoute(habitId)
                    )
                }
            )
        }

        composable(Routes.CREATE_HABIT) {
            CreateHabitScreen(
                onClose = {
                    navController.popBackStack()
                },
                onHabitSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.UPDATE_HABIT,
            arguments = listOf(
                navArgument(Routes.UPDATE_HABIT_ARG_HABIT_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateHabitScreen(
                onClose = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.PROGRESS) {
            ProgressScreen(navController)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }
    }
}

/**
 * Navigates to one of the bottom-nav tabs
 * while keeping a clean back stack.
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