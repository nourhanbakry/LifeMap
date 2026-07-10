package com.example.lifemap.presentation.Habits.viewModel

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
class HabitsViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitsUiState())
    val uiState: StateFlow<HabitsUiState> = _uiState.asStateFlow()

    init {
        observeHabits()
    }

    private fun observeHabits() {

        viewModelScope.launch {

            repository.observeHabits().collect { result ->

                result
                    .onSuccess { habits ->

                        _uiState.value = _uiState.value.copy(
                            habits = habits,
                            isLoading = false,
                            errorMessage = null
                        )

                    }
                    .onFailure { error ->

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )

                    }
            }
        }
    }

    fun onEvent(event: HabitsEvent) {

        when (event) {

            is HabitsEvent.DeleteHabit -> {

                viewModelScope.launch {
                    repository.deleteHabit(event.habitId)
                }

            }
        }
    }

}