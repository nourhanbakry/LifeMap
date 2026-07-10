package com.example.lifemap.presentation.Habits.viewModel

sealed class UpdateHabitEvent {

    object Increment : UpdateHabitEvent()

    object Decrement : UpdateHabitEvent()

}