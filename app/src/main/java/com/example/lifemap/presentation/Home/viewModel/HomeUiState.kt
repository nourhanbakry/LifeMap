package com.example.lifemap.presentation.Home.viewmodel

import com.example.lifemap.domain.entity.Task

data class HomeUiState(
    val userName: String = "",
    val selectedDateMillis: Long = System.currentTimeMillis(),
    val weekDatesMillis: List<Long> = emptyList(),
    val monthLabel: String = "",
    val selectedFilter: String = "All",
    val allTasksForDate: List<Task> = emptyList(),
    val filteredTasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
