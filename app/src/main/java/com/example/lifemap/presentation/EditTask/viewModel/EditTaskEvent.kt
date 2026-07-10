package com.example.lifemap.presentation.EditTask.viewmodel

sealed class EditTaskEvent {
    data class TitleChanged(val title: String) : EditTaskEvent()
    data class DescriptionChanged(val description: String) : EditTaskEvent()
    data class CategoryChanged(val category: String) : EditTaskEvent()
    data class DateChanged(val dateMillis: Long) : EditTaskEvent()
    data class StartTimeChanged(val time: String) : EditTaskEvent()
    data class EndTimeChanged(val time: String) : EditTaskEvent()
    data class PriorityChanged(val priority: String) : EditTaskEvent()
    data class NewSubtaskTextChanged(val text: String) : EditTaskEvent()
    data object AddSubtaskClicked : EditTaskEvent()
    data class ToggleSubtaskClicked(val subtaskId: String) : EditTaskEvent()
    data class RemoveSubtaskClicked(val subtaskId: String) : EditTaskEvent()
    data object UpdateClicked : EditTaskEvent()
    data object DeleteClicked : EditTaskEvent()
}
