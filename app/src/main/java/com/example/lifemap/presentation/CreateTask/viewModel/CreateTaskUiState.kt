package com.example.lifemap.presentation.CreateTask.viewmodel

import com.example.lifemap.domain.entity.Subtask
import com.example.lifemap.domain.entity.TaskCategory
import com.example.lifemap.domain.entity.TaskPriority

data class CreateTaskUiState(
    val title: String = "",
    val description: String = "",
    val category: String = TaskCategory.WORK,
    val dateMillis: Long = System.currentTimeMillis(),
    val startTime: String = "09:00 AM",
    val endTime: String = "10:00 AM",
    val priority: String = TaskPriority.MEDIUM,
    val subtasks: List<Subtask> = emptyList(),
    val newSubtaskText: String = "",

    val titleError: String? = null,

    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)
