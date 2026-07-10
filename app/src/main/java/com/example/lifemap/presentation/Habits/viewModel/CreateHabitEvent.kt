package com.example.lifemap.presentation.Habits.viewModel

sealed class CreateHabitEvent {

    data class NameChanged(val value: String) : CreateHabitEvent()

    data class GoalValueChanged(val value: String) : CreateHabitEvent()

    data class GoalUnitChanged(val value: String) : CreateHabitEvent()

    data class DayToggled(val day: Int) : CreateHabitEvent()

    data class ReminderToggled(val enabled: Boolean) : CreateHabitEvent()

    object Save : CreateHabitEvent()

}