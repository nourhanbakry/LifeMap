package com.example.lifemap.presentation.Progress.viewModel

import com.example.lifemap.domain.entity.Habit

data class ProgressUiState(

    val habits: List<Habit> = emptyList(),

    val isLoading: Boolean = true,

    val errorMessage: String? = null,

    val habitScore: Float = 0f,

    val productivityScore: Float = 0f,

    val healthScore: Float = 0f,

    val weeklyProgress: List<Float> = List(7) { 0f }

)