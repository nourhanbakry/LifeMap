package com.example.lifemap.presentation.Home.viewmodel

sealed class HomeEvent {
    data class DateSelected(val dateMillis: Long) : HomeEvent()
    data object PreviousMonthClicked : HomeEvent()
    data object NextMonthClicked : HomeEvent()
    data class FilterChanged(val filter: String) : HomeEvent()
    data class TaskStatusToggled(val taskId: String, val newStatus: String) : HomeEvent()
}
