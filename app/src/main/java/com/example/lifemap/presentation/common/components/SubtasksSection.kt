package com.example.lifemap.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifemap.domain.entity.Subtask

@Composable
fun SubtasksSection(
    subtasks: List<Subtask>,
    newSubtaskText: String,
    onNewSubtaskTextChange: (String) -> Unit,
    onAddSubtask: () -> Unit,
    onToggleSubtask: (String) -> Unit,
    onRemoveSubtask: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        subtasks.forEach { subtask ->

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = subtask.isDone,
                    onCheckedChange = { onToggleSubtask(subtask.id) },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF6D28D9))
                )

                Text(
                    text = subtask.title,
                    fontSize = 14.sp,
                    color = if (subtask.isDone) Color(0xFF94A3B8) else Color(0xFF0F172A),
                    textDecoration = if (subtask.isDone) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { onRemoveSubtask(subtask.id) }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Remove subtask",
                        tint = Color(0xFF94A3B8)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newSubtaskText,
                onValueChange = onNewSubtaskTextChange,
                placeholder = { Text("Add subtask", color = Color(0xFFCBD5E1)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            )

            IconButton(
                onClick = onAddSubtask,
                modifier = Modifier.background(Color(0xFFEFF6FF), RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add subtask", tint = Color(0xFF3B82F6))
            }
        }
    }
}
