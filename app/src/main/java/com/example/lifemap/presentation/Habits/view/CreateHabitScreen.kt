package com.example.lifemap.presentation.Habits.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifemap.presentation.Habits.viewModel.CreateHabitEvent
import com.example.lifemap.presentation.Habits.viewModel.CreateHabitViewModel

private val GradientStart = Color(0xFF3A2E8F)
private val GradientEnd = Color(0xFFC81E5C)

private val dayLabels = listOf("S", "M", "T", "W", "T", "F", "S")

@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onHabitSaved: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {

        if (uiState.isSaved) {
            onHabitSaved()
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "Create Habit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel(text = "Habit Name")

        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onEvent(CreateHabitEvent.NameChanged(it)) },
            placeholder = { Text("e.g., Drink Water") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = fieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        FieldLabel(text = "Goal Value")

        OutlinedTextField(
            value = uiState.goalValue,
            onValueChange = { viewModel.onEvent(CreateHabitEvent.GoalValueChanged(it)) },
            placeholder = { Text("e.g., 8 glasses, 30 minutes") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(14.dp),
            colors = fieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = uiState.goalUnit,
            onValueChange = { viewModel.onEvent(CreateHabitEvent.GoalUnitChanged(it)) },
            placeholder = { Text("Unit (e.g., glasses, minutes, pages)") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = fieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        FieldLabel(text = "Repeat Days")

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            dayLabels.forEachIndexed { index, label ->

                val isSelected = uiState.repeatDays.contains(index)

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            brush = if (isSelected)
                                Brush.linearGradient(listOf(GradientStart, GradientEnd))
                            else
                                Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable {
                            viewModel.onEvent(CreateHabitEvent.DayToggled(index))
                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = label,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 13.sp
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3F1FA), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFE4E0F7), CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = GradientStart
                )

            }

            Spacer(modifier = Modifier.size(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(text = "Daily Reminder", fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text(text = "Get notified every day", fontSize = 12.sp, color = Color.Gray)

            }

            Switch(
                checked = uiState.reminderEnabled,
                onCheckedChange = { viewModel.onEvent(CreateHabitEvent.ReminderToggled(it)) },
                colors = SwitchDefaults.colors(checkedTrackColor = GradientStart)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(GradientEnd.copy(alpha = 0.15f), GradientStart.copy(alpha = 0.15f))
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(16.dp)
        ) {

            Text(
                text = "Small steps lead to massive results.",
                fontSize = 13.sp,
                color = Color.DarkGray
            )

        }

        if (uiState.errorMessage != null) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = uiState.errorMessage ?: "",
                color = Color(0xFFC81E5C),
                fontSize = 13.sp
            )

        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                    shape = RoundedCornerShape(26.dp)
                )
                .clickable(enabled = !uiState.isSaving) {
                    viewModel.onEvent(CreateHabitEvent.Save)
                },
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = if (uiState.isSaving) "Saving..." else "Create Habit",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Composable
private fun FieldLabel(text: String) {

    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color.DarkGray,
        modifier = Modifier.padding(bottom = 6.dp)
    )

}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = Color.LightGray,
    focusedBorderColor = GradientStart,
    unfocusedContainerColor = Color(0xFFF7F7FA),
    focusedContainerColor = Color(0xFFF7F7FA)
)