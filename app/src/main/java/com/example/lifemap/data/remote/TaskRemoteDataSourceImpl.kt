package com.example.lifemap.data.remote

import com.example.lifemap.domain.entity.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

// Firestore applies writes to the local cache and notifies listeners
// immediately, but the Task returned by set()/update()/delete() only
// completes once the server acknowledges it - on a slow or offline
// connection that can take a very long time (or never happen while
// offline). WRITE_ACK_TIMEOUT_MS bounds that wait so the UI doesn't get
// stuck on "Saving..."; the write itself is unaffected and still syncs
// to the server as soon as connectivity allows.
private const val WRITE_ACK_TIMEOUT_MS = 6000L
<<<<<<< HEAD
=======

=======
import javax.inject.Inject

>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
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

<<<<<<< HEAD

            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                document.set(newTask).await()
            }

            document.set(newTask).await()

=======
<<<<<<< HEAD
            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                document.set(newTask).await()
            }
=======
            document.set(newTask).await()
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073

            Result.success(newTask)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {

        return try {

<<<<<<< HEAD

            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                tasksCollection.document(task.id).set(task).await()
            }

            tasksCollection.document(task.id).set(task).await()
=======
<<<<<<< HEAD
            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                tasksCollection.document(task.id).set(task).await()
            }
=======
            tasksCollection.document(task.id).set(task).await()
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {

        return try {

<<<<<<< HEAD

            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                tasksCollection.document(taskId).delete().await()
            }
            tasksCollection.document(taskId).delete().await()
=======
<<<<<<< HEAD
            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                tasksCollection.document(taskId).delete().await()
            }
=======
            tasksCollection.document(taskId).delete().await()
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    override suspend fun updateTaskStatus(taskId: String, status: String): Result<Unit> {

        return try {

<<<<<<< HEAD

            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                tasksCollection.document(taskId).update("status", status).await()
            }

            tasksCollection.document(taskId).update("status", status).await()
=======
<<<<<<< HEAD
            withTimeoutOrNull(WRITE_ACK_TIMEOUT_MS) {
                tasksCollection.document(taskId).update("status", status).await()
            }
=======
            tasksCollection.document(taskId).update("status", status).await()
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}
