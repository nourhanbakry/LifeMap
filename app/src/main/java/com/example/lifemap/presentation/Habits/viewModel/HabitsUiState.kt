package com.example.lifemap.presentation.Habits.viewModel

import com.example.lifemap.domain.entity.Habit

data class HabitsUiState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)