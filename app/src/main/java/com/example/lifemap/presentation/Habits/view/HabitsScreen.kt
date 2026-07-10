package com.example.lifemap.presentation.Habits.view

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifemap.domain.entity.Habit
import com.example.lifemap.presentation.Habits.viewModel.HabitsViewModel

private val GradientStart = Color(0xFF3A2E8F)
private val GradientEnd = Color(0xFFC81E5C)

@Composable
fun HabitsScreen(
    viewModel: HabitsViewModel = hiltViewModel(),
    onAddHabit: () -> Unit,
    onHabitClick: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Text(
                text = "Habits",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Build better habits every day",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {

                uiState.isLoading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }

                uiState.errorMessage != null -> {

                    Text(
                        text = "Something went wrong: ${uiState.errorMessage}",
                        color = Color(0xFFC81E5C)
                    )

                }

                uiState.habits.isEmpty() -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "No habits yet. Tap + to add your first habit.",
                            color = Color.Gray
                        )

                    }

                }

                else -> {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(
                            items = uiState.habits,
                            key = { habit -> habit.id }
                        ) { habit ->

                            HabitCard(
                                habit = habit,
                                onClick = { onHabitClick(habit.id) }
                            )

                        }

                    }

                }

            }

        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(56.dp)
                .background(
                    brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                    shape = CircleShape
                )
                .clickable { onAddHabit() },
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Habit",
                tint = Color.White
            )

        }

    }
}

@Composable
private fun HabitCard(
    habit: Habit,
    onClick: () -> Unit
) {

    val progressFraction =
        if (habit.goalValue > 0)
            (habit.currentProgress.toFloat() / habit.goalValue.toFloat()).coerceIn(0f, 1f)
        else
            0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7FA), RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {

        Text(
            text = habit.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Goal: ${habit.goalValue} ${habit.goalUnit}",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(progressFraction)
                    .height(8.dp)
                    .background(
                        brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                        shape = RoundedCornerShape(4.dp)
                    )
            )

        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${habit.currentProgress} / ${habit.goalValue}",
            fontSize = 12.sp,
            color = Color.Gray
        )

    }
}