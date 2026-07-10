package com.example.lifemap.presentation.Home.view

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lifemap.domain.entity.TaskStatus
import com.example.lifemap.presentation.Home.view.components.BottomNavBar
import com.example.lifemap.presentation.Home.view.components.CalendarStrip
import com.example.lifemap.presentation.Home.view.components.TaskCard
import com.example.lifemap.presentation.Home.viewmodel.HomeEvent
import com.example.lifemap.presentation.Home.viewmodel.HomeViewModel
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.Navigation.navigateToBottomNavRoute
import java.util.Calendar

@Composable
fun HomeScreen(navController: NavController) {

    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF8F9FC),
        bottomBar = {
            BottomNavBar(
                selectedLabel = "Home",
                onItemClick = { label ->
                    navController.navigateToBottomNavRoute(Routes.routeForBottomNavLabel(label))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.CREATE_TASK) },
                shape = CircleShape,
                containerColor = Color.Transparent,
                modifier = Modifier.background(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFF3730A3),
                            1.0f to Color(0xFFBE185D)
                        )
                    ),
                    shape = CircleShape
                )
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add task", tint = Color.White)
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
                        text = greetingForNow(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "You have ${state.allTasksForDate.size} tasks today",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B)
                    )
                }

                Box {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notifications",
                        tint = Color(0xFF0F172A),
                        modifier = Modifier.size(28.dp)
                    )
                    if (state.allTasksForDate.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(16.dp)
                                .background(Color(0xFFEF4444), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.allTasksForDate.size.toString(),
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ------ Calendar Strip ------
            CalendarStrip(
                monthLabel = state.monthLabel,
                weekDatesMillis = state.weekDatesMillis,
                selectedDateMillis = state.selectedDateMillis,
                onPreviousMonth = { viewModel.onEvent(HomeEvent.PreviousMonthClicked) },
                onNextMonth = { viewModel.onEvent(HomeEvent.NextMonthClicked) },
                onDaySelected = { viewModel.onEvent(HomeEvent.DateSelected(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ------ Filter Chips ------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TaskStatus.ALL_WITH_FILTER.forEach { filter ->

                    val isSelected = state.selectedFilter == filter

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isSelected) Color(0xFF6D28D9) else Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { viewModel.onEvent(HomeEvent.FilterChanged(filter)) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF64748B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ------ Task List ------
            if (state.filteredTasks.isEmpty() && !state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks for this day",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 96.dp)
                ) {
                    items(state.filteredTasks) { task ->
                        TaskCard(
                            task = task,
                            onClick = {
                                navController.navigate(Routes.editTaskRoute(task.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun greetingForNow(): String {

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    return when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }
}
