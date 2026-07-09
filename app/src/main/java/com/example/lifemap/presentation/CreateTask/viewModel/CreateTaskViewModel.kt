package com.example.lifemap.presentation.CreateTask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemap.data.local.UserPreferences
import com.example.lifemap.domain.entity.Subtask
import com.example.lifemap.domain.entity.Task
import com.example.lifemap.domain.entity.TaskStatus
import com.example.lifemap.domain.repoInterface.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskUiState())
    val uiState: StateFlow<CreateTaskUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

    fun onEvent(event: CreateTaskEvent) {
        when (event) {

            is CreateTaskEvent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(title = event.title, titleError = null)
            }

            is CreateTaskEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is CreateTaskEvent.CategoryChanged -> {
                _uiState.value = _uiState.value.copy(category = event.category)
            }

            is CreateTaskEvent.DateChanged -> {
                _uiState.value = _uiState.value.copy(dateMillis = event.dateMillis)
            }

            is CreateTaskEvent.StartTimeChanged -> {
                _uiState.value = _uiState.value.copy(startTime = event.time)
            }

            is CreateTaskEvent.EndTimeChanged -> {
                _uiState.value = _uiState.value.copy(endTime = event.time)
            }

            is CreateTaskEvent.PriorityChanged -> {
                _uiState.value = _uiState.value.copy(priority = event.priority)
            }

            is CreateTaskEvent.NewSubtaskTextChanged -> {
                _uiState.value = _uiState.value.copy(newSubtaskText = event.text)
            }

            CreateTaskEvent.AddSubtaskClicked -> addSubtask()

            is CreateTaskEvent.ToggleSubtaskClicked -> toggleSubtask(event.subtaskId)

            is CreateTaskEvent.RemoveSubtaskClicked -> removeSubtask(event.subtaskId)

            CreateTaskEvent.CreateClicked -> createTask()
        }
    }

    private fun addSubtask() {

        val text = _uiState.value.newSubtaskText.trim()

        if (text.isEmpty()) return

        val newSubtask = Subtask(id = UUID.randomUUID().toString(), title = text)

        _uiState.value = _uiState.value.copy(
            subtasks = _uiState.value.subtasks + newSubtask,
            newSubtaskText = ""
        )
    }

    private fun toggleSubtask(subtaskId: String) {

        val updated = _uiState.value.subtasks.map {
            if (it.id == subtaskId) it.copy(isDone = !it.isDone) else it
        }

        _uiState.value = _uiState.value.copy(subtasks = updated)
    }

    private fun removeSubtask(subtaskId: String) {

        _uiState.value = _uiState.value.copy(
            subtasks = _uiState.value.subtasks.filterNot { it.id == subtaskId }
        )
    }

    private fun createTask() {

        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.value = state.copy(titleError = "Task title is required")
            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isSaving = true, error = null)

            val user = userPreferences.getUser()

            if (user == null) {
                _uiState.value = _uiState.value.copy(isSaving = false, error = "You must be logged in")
                return@launch
            }

            val task = Task(
                userId = user.uid,
                title = state.title.trim(),
                description = state.description.trim(),
                category = state.category,
                date = dateFormat.format(state.dateMillis),
                startTime = state.startTime,
                endTime = state.endTime,
                priority = state.priority,
                status = TaskStatus.TO_DO,
                subtasks = state.subtasks
            )

            val result = taskRepository.addTask(task)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(isSaving = false, isSaved = true)
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(isSaving = false, error = e.message)
            }
        }
    }
}
