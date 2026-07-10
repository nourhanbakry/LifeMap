package com.example.lifemap.presentation.Habits.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemap.domain.entity.Habit
import com.example.lifemap.domain.repoInterface.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateHabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateHabitUiState())
    val uiState: StateFlow<CreateHabitUiState> = _uiState.asStateFlow()

    fun onEvent(event: CreateHabitEvent) {

        when (event) {

            is CreateHabitEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.value)
            }

            is CreateHabitEvent.GoalValueChanged -> {
                _uiState.value = _uiState.value.copy(goalValue = event.value)
            }

            is CreateHabitEvent.GoalUnitChanged -> {
                _uiState.value = _uiState.value.copy(goalUnit = event.value)
            }

            is CreateHabitEvent.DayToggled -> {

                val currentDays = _uiState.value.repeatDays

                val updatedDays =
                    if (currentDays.contains(event.day))
                        currentDays - event.day
                    else
                        currentDays + event.day

                _uiState.value = _uiState.value.copy(repeatDays = updatedDays)

            }

            is CreateHabitEvent.ReminderToggled -> {
                _uiState.value = _uiState.value.copy(reminderEnabled = event.enabled)
            }

            CreateHabitEvent.Save -> {
                saveHabit()
            }
        }
    }

    private fun saveHabit() {

        val state = _uiState.value

        if (state.name.isBlank() || state.goalValue.isBlank()) {

            _uiState.value = state.copy(
                errorMessage = "Please fill in the habit name and goal"
            )

            return

        }

        val goalValueInt = state.goalValue.toIntOrNull()

        if (goalValueInt == null) {

            _uiState.value = state.copy(
                errorMessage = "Goal value must be a number"
            )

            return

        }

        _uiState.value = state.copy(isSaving = true, errorMessage = null)

        viewModelScope.launch {

            val habit = Habit(
                name = state.name,
                goalValue = goalValueInt,
                goalUnit = state.goalUnit,
                repeatDays = state.repeatDays,
                reminderEnabled = state.reminderEnabled
            )

            val result = repository.addHabit(habit)

            result
                .onSuccess {

                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        isSaved = true
                    )

                }
                .onFailure { error ->

                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        errorMessage = error.message
                    )

                }

        }
    }

}