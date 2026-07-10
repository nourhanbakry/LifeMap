package com.example.lifemap.presentation.Habits.viewModel

sealed class HabitsEvent {

    data class DeleteHabit(val habitId: String) : HabitsEvent()

}