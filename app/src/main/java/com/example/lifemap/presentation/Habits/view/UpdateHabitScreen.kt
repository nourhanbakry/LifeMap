package com.example.lifemap.presentation.Habits.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lifemap.presentation.Habits.viewModel.UpdateHabitEvent
import com.example.lifemap.presentation.Habits.viewModel.UpdateHabitViewModel

private val GradientStart = Color(0xFF3A2E8F)
private val GradientEnd = Color(0xFFC81E5C)

@Composable
fun UpdateHabitScreen(
    viewModel: UpdateHabitViewModel = hiltViewModel(),
    onClose: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }

            Text(
                text = "Update Habit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }

        if (uiState.isLoading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = uiState.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = GradientStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Your daily goal is ${uiState.goalValue} ${uiState.goalUnit}",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3F1FA), RoundedCornerShape(20.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "CURRENT PROGRESS",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.White, CircleShape)
                            .clickable(enabled = !uiState.isSaving) {
                                viewModel.onEvent(UpdateHabitEvent.Decrement)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
                    }

                    Spacer(modifier = Modifier.size(24.dp))

                    Text(
                        text = "${uiState.currentProgress}",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = " / ${uiState.goalValue}",
                        fontSize = 20.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.size(24.dp))

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.White, CircleShape)
                            .clickable(enabled = !uiState.isSaving) {
                                viewModel.onEvent(UpdateHabitEvent.Increment)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            val remaining = uiState.goalValue - uiState.currentProgress

            if (remaining > 0) {

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFFFCE4EC), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    Text(
                        text = "$remaining ${uiState.goalUnit} left to hit your goal!",
                        fontSize = 13.sp,
                        color = Color(0xFFC81E5C)
                    )

                }

            } else {

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFFE0F7E9), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {

                    Text(
                        text = "Goal completed! 🎉",
                        fontSize = 13.sp,
                        color = Color(0xFF2E7D32)
                    )

                }

            }

            if (uiState.errorMessage != null) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = uiState.errorMessage ?: "",
                    color = Color(0xFFC81E5C),
                    fontSize = 13.sp
                )

            }

        }

    }
}