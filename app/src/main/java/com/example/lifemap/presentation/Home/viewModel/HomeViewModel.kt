package com.example.lifemap.presentation.Home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemap.data.local.UserPreferences
import com.example.lifemap.domain.entity.Task
import com.example.lifemap.domain.repoInterface.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

    private var allTasks: List<Task> = emptyList()
    private var currentUserId: String? = null

    init {

        val today = startOfDay(System.currentTimeMillis())

        _uiState.value = _uiState.value.copy(
            selectedDateMillis = today,
<<<<<<< HEAD
            weekDatesMillis = computeWeek(today),
            monthLabel = monthLabel(today),
=======
<<<<<<< HEAD
            weekDatesMillis = computeMonthDays(today),
=======
            weekDatesMillis = computeWeek(today),
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
            monthLabel = monthLabel(today)
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
        )

        loadUserAndTasks()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.DateSelected -> selectDate(event.dateMillis)

            HomeEvent.PreviousMonthClicked -> shiftMonth(-1)

            HomeEvent.NextMonthClicked -> shiftMonth(1)

            is HomeEvent.FilterChanged -> {
                _uiState.value = _uiState.value.copy(selectedFilter = event.filter)
                applyFilter()
            }

            is HomeEvent.TaskStatusToggled -> toggleStatus(event.taskId, event.newStatus)
        }
    }

    private fun loadUserAndTasks() {

        viewModelScope.launch {

            val user = userPreferences.getUser()
            currentUserId = user?.uid

            _uiState.value = _uiState.value.copy(userName = user?.fullName ?: "")

            val uid = currentUserId

            if (uid == null) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                return@launch
            }

            taskRepository.getTasks(uid).collect { tasks ->
                allTasks = tasks
                applyDateFilter()
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun selectDate(dateMillis: Long) {

        val normalized = startOfDay(dateMillis)

        _uiState.value = _uiState.value.copy(
            selectedDateMillis = normalized,
<<<<<<< HEAD
            weekDatesMillis = computeWeek(normalized),
=======
<<<<<<< HEAD
            weekDatesMillis = computeMonthDays(normalized),
=======
            weekDatesMillis = computeWeek(normalized),
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
            monthLabel = monthLabel(normalized)
        )

        applyDateFilter()
    }

    private fun shiftMonth(amount: Int) {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = _uiState.value.selectedDateMillis
        calendar.add(Calendar.MONTH, amount)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val newDate = startOfDay(calendar.timeInMillis)

        _uiState.value = _uiState.value.copy(
            selectedDateMillis = newDate,
<<<<<<< HEAD
            weekDatesMillis = computeWeek(newDate),
=======
<<<<<<< HEAD
            weekDatesMillis = computeMonthDays(newDate),
=======
            weekDatesMillis = computeWeek(newDate),
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
            monthLabel = monthLabel(newDate)
        )

        applyDateFilter()
    }

    private fun applyDateFilter() {

        val selectedDateString = dateFormat.format(_uiState.value.selectedDateMillis)

        val tasksForDate = allTasks.filter { it.date == selectedDateString }

        _uiState.value = _uiState.value.copy(allTasksForDate = tasksForDate)

        applyFilter()
    }

    private fun applyFilter() {

        val state = _uiState.value

        val filtered = if (state.selectedFilter == "All") {
            state.allTasksForDate
        } else {
            state.allTasksForDate.filter { it.status == state.selectedFilter }
        }

        _uiState.value = state.copy(filteredTasks = filtered)
    }

    private fun toggleStatus(taskId: String, newStatus: String) {

        viewModelScope.launch {
            taskRepository.updateTaskStatus(taskId, newStatus)
        }
    }

    private fun startOfDay(millis: Long): Long {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
    private fun computeMonthDays(dateMillis: Long): List<Long> {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val days = mutableListOf<Long>()

        for (i in 0 until daysInMonth) {
            days.add(calendar.timeInMillis)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return days
=======
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
    private fun computeWeek(dateMillis: Long): List<Long> {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // SUNDAY=1 .. SATURDAY=7
        val mondayBasedIndex = (dayOfWeek + 5) % 7 // MONDAY=0 .. SUNDAY=6

        calendar.add(Calendar.DAY_OF_MONTH, -mondayBasedIndex)

        val week = mutableListOf<Long>()

        for (i in 0..6) {
            week.add(calendar.timeInMillis)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return week
<<<<<<< HEAD
=======
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
    }

    private fun monthLabel(dateMillis: Long): String {

        val format = SimpleDateFormat("MMMM yyyy", Locale.US)

        return format.format(dateMillis)
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
