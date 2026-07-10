package com.example.lifemap.presentation.common.components

import androidx.compose.ui.graphics.Color
import com.example.lifemap.domain.entity.TaskCategory
import com.example.lifemap.domain.entity.TaskPriority
import com.example.lifemap.domain.entity.TaskStatus

fun categoryColor(category: String): Color {
    return when (category) {
        TaskCategory.WORK -> Color(0xFF3B82F6)
        TaskCategory.STUDY -> Color(0xFFA855F7)
        TaskCategory.HEALTH -> Color(0xFFEF4444)
        TaskCategory.PERSONAL -> Color(0xFF10B981)
        else -> Color(0xFF94A3B8)
    }
}

fun priorityColor(priority: String): Color {
    return when (priority) {
        TaskPriority.HIGH -> Color(0xFFEF4444)
        TaskPriority.MEDIUM -> Color(0xFFF59E0B)
        TaskPriority.LOW -> Color(0xFF94A3B8)
        else -> Color(0xFF94A3B8)
    }
}

fun statusColor(status: String): Color {
    return when (status) {
        TaskStatus.TO_DO -> Color(0xFF94A3B8)
        TaskStatus.IN_PROGRESS -> Color(0xFF3B82F6)
        TaskStatus.DONE -> Color(0xFF10B981)
        else -> Color(0xFF94A3B8)
    }
}
