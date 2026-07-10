package com.example.lifemap.data.repoImpl

import com.example.lifemap.data.remote.HabitRemoteDataSource
import com.example.lifemap.domain.entity.Habit
import com.example.lifemap.domain.repoInterface.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val remote: HabitRemoteDataSource
) : HabitRepository {

    override fun observeHabits(): Flow<Result<List<Habit>>> {
        return remote.observeHabits()
    }

    override suspend fun addHabit(habit: Habit): Result<Unit> {
        return remote.addHabit(habit)
    }

    override suspend fun updateProgress(
        habitId: String,
        newProgress: Int
    ): Result<Unit> {
        return remote.updateProgress(habitId, newProgress)
    }

    override suspend fun deleteHabit(habitId: String): Result<Unit> {
        return remote.deleteHabit(habitId)
    }

}