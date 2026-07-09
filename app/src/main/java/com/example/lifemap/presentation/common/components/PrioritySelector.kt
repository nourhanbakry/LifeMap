package com.example.lifemap.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifemap.domain.entity.TaskPriority

@Composable
fun PrioritySelector(
    selected: String,
    onSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TaskPriority.ALL.forEach { priority ->

            val isSelected = priority == selected
            val color = priorityColor(priority)

            Row(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF3B82F6) else Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = if (isSelected) Color(0xFFEFF6FF) else Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onSelected(priority) }
                    .padding(horizontal = 10.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color, CircleShape)
                )
                Text(
                    text = priority,
                    fontSize = 13.sp,
                    color = Color(0xFF0F172A),
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}
