package com.example.lifemap.presentation.Habits.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemap.domain.repoInterface.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateHabitViewModel @Inject constructor(
    private val repository: HabitRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val habitId: String = savedStateHandle.get<String>("habitId") ?: ""

    private val _uiState = MutableStateFlow(UpdateHabitUiState())
    val uiState: StateFlow<UpdateHabitUiState> = _uiState.asStateFlow()

    init {
        loadHabit()
    }

    private fun loadHabit() {

        viewModelScope.launch {

            repository.observeHabits().collect { result ->

                result.onSuccess { habits ->

                    val habit = habits.find { it.id == habitId }

                    if (habit != null) {

                        _uiState.value = _uiState.value.copy(
                            habitId = habit.id,
                            name = habit.name,
                            goalValue = habit.goalValue,
                            goalUnit = habit.goalUnit,
                            currentProgress = habit.currentProgress,
                            isLoading = false
                        )

                    }

                }

            }

        }
    }

    fun onEvent(event: UpdateHabitEvent) {

        when (event) {

            UpdateHabitEvent.Increment -> {
                updateProgress(_uiState.value.currentProgress + 1)
            }

            UpdateHabitEvent.Decrement -> {

                val newValue = (_uiState.value.currentProgress - 1).coerceAtLeast(0)
                updateProgress(newValue)

            }

        }
    }

    private fun updateProgress(newValue: Int) {

        _uiState.value = _uiState.value.copy(currentProgress = newValue, isSaving = true)

        viewModelScope.launch {

            val result = repository.updateProgress(habitId, newValue)

            result
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isSaving = false)
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