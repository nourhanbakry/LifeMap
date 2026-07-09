package com.example.lifemap.domain.entity

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = TaskCategory.WORK,
    val date: String = "", // stored as MM/dd/yyyy
    val startTime: String = "",
    val endTime: String = "",
    val priority: String = TaskPriority.MEDIUM,
    val status: String = TaskStatus.TO_DO,
    val subtasks: List<Subtask> = emptyList(),
    val attachmentUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

object TaskCategory {
    const val WORK = "Work"
    const val STUDY = "Study"
    const val HEALTH = "Health"
    const val PERSONAL = "Personal"

    val ALL = listOf(WORK, STUDY, HEALTH, PERSONAL)
}

object TaskPriority {
    const val HIGH = "High"
    const val MEDIUM = "Medium"
    const val LOW = "Low"

    val ALL = listOf(HIGH, MEDIUM, LOW)
}

object TaskStatus {
    const val TO_DO = "To Do"
    const val IN_PROGRESS = "In Progress"
    const val DONE = "Done"

    val ALL = listOf(TO_DO, IN_PROGRESS, DONE)
    val ALL_WITH_FILTER = listOf("All") + ALL
}
