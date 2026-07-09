package com.example.lifemap.data.remote

import com.example.lifemap.domain.entity.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRemoteDataSource {

    private val tasksCollection = firestore.collection("Tasks")

    override fun getTasks(userId: String): Flow<List<Task>> = callbackFlow {

        val listener = tasksCollection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val tasks = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(Task::class.java)
                } ?: emptyList()

                trySend(tasks)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun getTaskById(taskId: String): Result<Task> {

        return try {

            val document = tasksCollection.document(taskId).get().await()

            val task = document.toObject(Task::class.java)
                ?: throw Exception("Task not found")

            Result.success(task)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun addTask(task: Task): Result<Task> {

        return try {

            val document = tasksCollection.document()
            val newTask = task.copy(id = document.id)

            document.set(newTask).await()

            Result.success(newTask)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {

        return try {

            tasksCollection.document(task.id).set(task).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {

        return try {

            tasksCollection.document(taskId).delete().await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun updateTaskStatus(taskId: String, status: String): Result<Unit> {

        return try {

            tasksCollection.document(taskId).update("status", status).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}
