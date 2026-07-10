package com.example.lifemap.data.remote

import com.example.lifemap.domain.entity.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRemoteDataSource {

    fun observeHabits(): Flow<Result<List<Habit>>>

    suspend fun addHabit(habit: Habit): Result<Unit>

    suspend fun updateProgress(
        habitId: String,
        newProgress: Int
    ): Result<Unit>

    suspend fun deleteHabit(habitId: String): Result<Unit>

}