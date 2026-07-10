package com.example.lifemap.data.remote

import com.example.lifemap.domain.entity.Habit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HabitRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : HabitRemoteDataSource {

    private fun habitsCollection() =
        firestore
            .collection("Users")
            .document(auth.currentUser!!.uid)
            .collection("Habits")


    override fun observeHabits(): Flow<Result<List<Habit>>> = callbackFlow {

        val registration = habitsCollection()
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                }

                val habits = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Habit::class.java)?.copy(id = doc.id)
                } ?: emptyList()

                trySend(Result.success(habits))
            }

        awaitClose { registration.remove() }
    }


    override suspend fun addHabit(habit: Habit): Result<Unit> {

        return try {

            habitsCollection()
                .add(habit)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    override suspend fun updateProgress(
        habitId: String,
        newProgress: Int
    ): Result<Unit> {

        return try {

            habitsCollection()
                .document(habitId)
                .update("currentProgress", newProgress)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }


    override suspend fun deleteHabit(habitId: String): Result<Unit> {

        return try {

            habitsCollection()
                .document(habitId)
                .delete()
                .await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

}