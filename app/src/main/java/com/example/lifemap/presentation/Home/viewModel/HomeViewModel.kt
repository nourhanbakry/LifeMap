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
            weekDatesMillis = computeMonthDays(today),
            monthLabel = monthLabel(today)
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
            weekDatesMillis = computeMonthDays(normalized),
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
            weekDatesMillis = computeMonthDays(newDate),
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
    }

    private fun monthLabel(dateMillis: Long): String {

        val format = SimpleDateFormat("MMMM yyyy", Locale.US)

        return format.format(dateMillis)
    }
}
