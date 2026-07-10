package com.example.lifemap.presentation.Habits.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import com.example.lifemap.domain.entity.Habit
import com.example.lifemap.presentation.Habits.viewModel.HabitsViewModel
import com.example.lifemap.presentation.Home.view.components.BottomNavBar
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.Navigation.navigateToBottomNavRoute
import kotlin.math.roundToInt

private val GradientStart = Color(0xFF3A2E8F)
private val GradientEnd = Color(0xFFC81E5C)

@Composable
fun HabitsScreen(
    navController: NavController,
    viewModel: HabitsViewModel = hiltViewModel(),
    onAddHabit: () -> Unit,
    onHabitClick: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFF8F9FC),
        bottomBar = {
            BottomNavBar(
                selectedLabel = "Habits",
                onItemClick = { label ->
                    navController.navigateToBottomNavRoute(Routes.routeForBottomNavLabel(label))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddHabit,
                shape = CircleShape,
                containerColor = Color.Transparent,
                modifier = Modifier.background(
                    brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Habit",
                    tint = Color.White
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // ------ Header ------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column {
                    Text(
                        text = "Habits",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Build better habits every day",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                }

                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFF0F172A),
                    modifier = Modifier.size(28.dp)
                )

            }

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
                            color = Color(0xFF94A3B8)
                        )

                    }

                }

                else -> {

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 96.dp)
                    ) {

                        items(
                            items = uiState.habits,
                            key = { habit -> habit.id }
                        ) { habit ->

                            HabitCard(
                                habit = habit,
                                onUpdateProgress = { onHabitClick(habit.id) }
                            )

                        }

                    }

                }

            }

        }

    }
}

@Composable
private fun HabitCard(
    habit: Habit,
    onUpdateProgress: () -> Unit
) {

    val progressFraction =
        if (habit.goalValue > 0)
            (habit.currentProgress.toFloat() / habit.goalValue.toFloat()).coerceIn(0f, 1f)
        else
            0f

    val progressPercent = (progressFraction * 100).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = habit.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Box(
                modifier = Modifier
                    .background(Color(0xFFF3E8FF), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = habit.streak.toString(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7C3AED)
                )
            }

        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Goal: ${habit.goalValue} ${habit.goalUnit}",
            fontSize = 13.sp,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFFE5E7EB), RoundedCornerShape(4.dp))
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "${habit.currentProgress} / ${habit.goalValue}",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )

            Text(
                text = "$progressPercent%",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )

        }

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(listOf(GradientStart, GradientEnd)),
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable { onUpdateProgress() }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Update Progress",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

        }

    }
}