package com.example.lifemap.presentation.EditTask.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemap.domain.entity.Subtask
import com.example.lifemap.domain.entity.Task
import com.example.lifemap.domain.repoInterface.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTaskUiState())
    val uiState: StateFlow<EditTaskUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

    init {

        val taskId: String = savedStateHandle.get<String>("taskId") ?: ""

        _uiState.value = _uiState.value.copy(taskId = taskId)

        loadTask(taskId)
    }

    fun onEvent(event: EditTaskEvent) {
        when (event) {

            is EditTaskEvent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(title = event.title, titleError = null)
            }

            is EditTaskEvent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is EditTaskEvent.CategoryChanged -> {
                _uiState.value = _uiState.value.copy(category = event.category)
            }

            is EditTaskEvent.DateChanged -> {
                _uiState.value = _uiState.value.copy(dateMillis = event.dateMillis)
            }

            is EditTaskEvent.StartTimeChanged -> {
                _uiState.value = _uiState.value.copy(startTime = event.time)
            }

            is EditTaskEvent.EndTimeChanged -> {
                _uiState.value = _uiState.value.copy(endTime = event.time)
            }

            is EditTaskEvent.PriorityChanged -> {
                _uiState.value = _uiState.value.copy(priority = event.priority)
            }

            is EditTaskEvent.NewSubtaskTextChanged -> {
                _uiState.value = _uiState.value.copy(newSubtaskText = event.text)
            }

            EditTaskEvent.AddSubtaskClicked -> addSubtask()

            is EditTaskEvent.ToggleSubtaskClicked -> toggleSubtask(event.subtaskId)

            is EditTaskEvent.RemoveSubtaskClicked -> removeSubtask(event.subtaskId)

            EditTaskEvent.UpdateClicked -> updateTask()

            EditTaskEvent.DeleteClicked -> deleteTask()
        }
    }

    private fun loadTask(taskId: String) {

        viewModelScope.launch {

            val result = taskRepository.getTaskById(taskId)

            result.onSuccess { task ->

                val millis = try {
                    dateFormat.parse(task.date)?.time ?: System.currentTimeMillis()
                } catch (e: ParseException) {
                    System.currentTimeMillis()
                }

                _uiState.value = _uiState.value.copy(
                    userId = task.userId,
                    title = task.title,
                    description = task.description,
                    category = task.category,
                    dateMillis = millis,
                    startTime = task.startTime,
                    endTime = task.endTime,
                    priority = task.priority,
                    status = task.status,
                    subtasks = task.subtasks,
                    attachmentUrl = task.attachmentUrl,
                    isLoading = false
                )

            }.onFailure { e ->

                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)

            }
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

    private fun updateTask() {

        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.value = state.copy(titleError = "Task title is required")
            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isSaving = true, error = null)

            val task = Task(
                id = state.taskId,
                userId = state.userId,
                title = state.title.trim(),
                description = state.description.trim(),
                category = state.category,
                date = dateFormat.format(state.dateMillis),
                startTime = state.startTime,
                endTime = state.endTime,
                priority = state.priority,
                status = state.status,
                subtasks = state.subtasks,
                attachmentUrl = state.attachmentUrl
            )

            val result = taskRepository.updateTask(task)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(isSaving = false, isSaved = true)
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(isSaving = false, error = e.message)
            }
        }
    }

    private fun deleteTask() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isSaving = true, error = null)

            val result = taskRepository.deleteTask(_uiState.value.taskId)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(isSaving = false, isDeleted = true)
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(isSaving = false, error = e.message)
            }
        }
    }
}
