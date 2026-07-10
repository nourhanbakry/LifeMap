package com.example.lifemap.presentation.Habits.viewModel

data class UpdateHabitUiState(
    val habitId: String = "",
    val name: String = "",
    val goalValue: Int = 0,
    val goalUnit: String = "",
    val currentProgress: Int = 0,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)