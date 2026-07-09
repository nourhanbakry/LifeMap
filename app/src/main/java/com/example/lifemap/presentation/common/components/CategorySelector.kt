package com.example.lifemap.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifemap.domain.entity.TaskCategory

@Composable
fun CategorySelector(
    selected: String,
    onSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {
        items(TaskCategory.ALL) { category ->

            val isSelected = category == selected
            val color = categoryColor(category)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF3B82F6) else Color(0xFFE2E8F0),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = if (isSelected) Color(0xFFEFF6FF) else Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onSelected(category) }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color, CircleShape)
                )
                Text(
                    text = category,
                    fontSize = 14.sp,
                    color = Color(0xFF0F172A),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
