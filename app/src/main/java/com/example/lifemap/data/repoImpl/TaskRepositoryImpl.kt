package com.example.lifemap.data.repoImpl

import com.example.lifemap.data.remote.TaskRemoteDataSource
import com.example.lifemap.domain.entity.Task
import com.example.lifemap.domain.repoInterface.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remote: TaskRemoteDataSource
) : TaskRepository {

    override fun getTasks(userId: String): Flow<List<Task>> {
        return remote.getTasks(userId)
    }

    override suspend fun getTaskById(taskId: String): Result<Task> {
        return remote.getTaskById(taskId)
    }

    override suspend fun addTask(task: Task): Result<Task> {
        return remote.addTask(task)
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return remote.updateTask(task)
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return remote.deleteTask(taskId)
    }

    override suspend fun updateTaskStatus(taskId: String, status: String): Result<Unit> {
        return remote.updateTaskStatus(taskId, status)
    }
}
