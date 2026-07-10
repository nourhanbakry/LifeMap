package com.example.lifemap.domain.repoInterface

import com.example.lifemap.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks(userId: String): Flow<List<Task>>

    suspend fun getTaskById(taskId: String): Result<Task>

    suspend fun addTask(task: Task): Result<Task>

    suspend fun updateTask(task: Task): Result<Unit>

    suspend fun deleteTask(taskId: String): Result<Unit>

    suspend fun updateTaskStatus(taskId: String, status: String): Result<Unit>

}
