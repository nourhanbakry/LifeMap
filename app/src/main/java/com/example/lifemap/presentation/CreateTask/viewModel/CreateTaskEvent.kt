package com.example.lifemap.presentation.CreateTask.viewmodel

sealed class CreateTaskEvent {
    data class TitleChanged(val title: String) : CreateTaskEvent()
    data class DescriptionChanged(val description: String) : CreateTaskEvent()
    data class CategoryChanged(val category: String) : CreateTaskEvent()
    data class DateChanged(val dateMillis: Long) : CreateTaskEvent()
    data class StartTimeChanged(val time: String) : CreateTaskEvent()
    data class EndTimeChanged(val time: String) : CreateTaskEvent()
    data class PriorityChanged(val priority: String) : CreateTaskEvent()
    data class NewSubtaskTextChanged(val text: String) : CreateTaskEvent()
    data object AddSubtaskClicked : CreateTaskEvent()
    data class ToggleSubtaskClicked(val subtaskId: String) : CreateTaskEvent()
    data class RemoveSubtaskClicked(val subtaskId: String) : CreateTaskEvent()
    data object CreateClicked : CreateTaskEvent()
}
