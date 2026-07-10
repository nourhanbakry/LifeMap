package com.example.lifemap.domain.entity

data class Habit(
    val id: String = "",
    val name: String = "",
    val iconKey: String = "default",
    val goalValue: Int = 0,
    val goalUnit: String = "",
    val repeatDays: List<Int> = emptyList(),
    val reminderEnabled: Boolean = true,
    val currentProgress: Int = 0,
    val streak: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)