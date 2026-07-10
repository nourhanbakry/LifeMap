package com.example.lifemap.presentation.Navigation

object Routes {
    const val WELCOME = "Welcome"
    const val LOGIN = "Login"
    const val SIGNUP = "SignUp"
    const val FORGOT_PASSWORD = "ForgotPassword"
    const val HOME = "Home"
    const val CREATE_TASK = "CreateTask"

    const val EDIT_TASK_ARG_TASK_ID = "taskId"
    const val EDIT_TASK_ROUTE = "EditTask/{$EDIT_TASK_ARG_TASK_ID}"

    fun editTaskRoute(taskId: String) = "EditTask/$taskId"

    // Bottom navigation tabs.
    // Habits, Progress, and Settings are placeholder routes for now -
    // teammates can build the real screens on top of them.
    const val HABITS = "Habits"
    const val PROGRESS = "Progress"
    const val SETTINGS = "Settings"

    // Habits feature routes
    const val CREATE_HABIT = "CreateHabit"

    const val UPDATE_HABIT_ARG_HABIT_ID = "habitId"
    const val UPDATE_HABIT = "UpdateHabit/{$UPDATE_HABIT_ARG_HABIT_ID}"

    fun updateHabitRoute(habitId: String) = "UpdateHabit/$habitId"

    /**
     * Maps a BottomNavBar item label to its route.
     * Kept in one place so adding/renaming a tab only needs a single edit.
     */
    fun routeForBottomNavLabel(label: String): String = when (label) {
        HOME -> HOME
        HABITS -> HABITS
        PROGRESS -> PROGRESS
        SETTINGS -> SETTINGS
        else -> HOME
    }
}
