package com.example.lifemap.presentation.Habits.viewModel

data class CreateHabitUiState(
    val name: String = "",
    val goalValue: String = "",
    val goalUnit: String = "",
    val repeatDays: List<Int> = emptyList(),
    val reminderEnabled: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)