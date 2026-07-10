package com.example.lifemap.presentation.Progress.viewModel

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
class ProgressViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    init {
        observeProgress()
    }

    private fun observeProgress() {

        viewModelScope.launch {

            repository.observeHabits().collect { result ->

                result
                    .onSuccess { habits ->

                        _uiState.value = ProgressUiState(
                            habits = habits,
                            isLoading = false,
                            habitScore = calculateHabitScore(habits),
                            productivityScore = calculateProductivity(habits),
                            healthScore = calculateHealthScore(habits),
                            weeklyProgress = createWeeklyProgress(habits)
                        )

                    }

                    .onFailure {

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )

                    }

            }

        }

    }

    fun onEvent(event: ProgressEvent){

        when(event){

            ProgressEvent.Refresh ->{
                observeProgress()
            }

        }

    }

    private fun calculateHabitScore(
        habits: List<Habit>
    ): Float {

        if(habits.isEmpty()) return 0f

        val score = habits.map {

            if(it.goalValue == 0)
                0f
            else
                (it.currentProgress.toFloat()/it.goalValue.toFloat())*100f

        }

        return score.average().toFloat().coerceAtMost(100f)

    }

    private fun calculateProductivity(
        habits: List<Habit>
    ): Float {

        if(habits.isEmpty()) return 0f

        val completed = habits.count {

            it.currentProgress >= it.goalValue

        }

        return (completed.toFloat()/habits.size.toFloat())*100f

    }

    private fun calculateHealthScore(
        habits: List<Habit>
    ): Float {

        if(habits.isEmpty()) return 0f

        val streakAverage = habits.map {

            it.streak.toFloat()

        }.average()

        return (streakAverage*10f).coerceAtMost(100f)

    }

    private fun createWeeklyProgress(
        habits: List<Habit>
    ): List<Float>{

        val score = calculateHabitScore(habits)

        return List(7){

            (score-(6-it)*5).coerceAtLeast(0f)

        }

    }

}