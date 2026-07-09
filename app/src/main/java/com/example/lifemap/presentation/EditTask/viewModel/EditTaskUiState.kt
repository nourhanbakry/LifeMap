package com.example.lifemap.presentation.EditTask.viewmodel

import com.example.lifemap.domain.entity.Subtask
import com.example.lifemap.domain.entity.TaskCategory
import com.example.lifemap.domain.entity.TaskPriority
import com.example.lifemap.domain.entity.TaskStatus

data class EditTaskUiState(
    val taskId: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = TaskCategory.WORK,
    val dateMillis: Long = System.currentTimeMillis(),
    val startTime: String = "09:00 AM",
    val endTime: String = "10:00 AM",
    val priority: String = TaskPriority.MEDIUM,
    val status: String = TaskStatus.TO_DO,
    val subtasks: List<Subtask> = emptyList(),
    val newSubtaskText: String = "",
    val attachmentUrl: String = "",

    val titleError: String? = null,

    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null
)
