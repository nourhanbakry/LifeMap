package com.example.lifemap.presentation.Progress.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.lifemap.presentation.Home.view.components.BottomNavBar
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.Navigation.navigateToBottomNavRoute
import com.example.lifemap.presentation.Progress.viewModel.ProgressViewModel

@Composable
fun ProgressScreen(
    navController: NavController,
    viewModel: ProgressViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(

        bottomBar = {

            BottomNavBar(

                selectedLabel = "Progress",

                onItemClick = { label ->

                    navController.navigateToBottomNavRoute(
                        Routes.routeForBottomNavLabel(label)
                    )

                }

            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            Text(
                text = "Your Progress",
                style = MaterialTheme.typography.headlineSmall
            )

            ProgressCard(
                title = "Health Score",
                score = state.healthScore
            )

            ProgressCard(
                title = "Productivity",
                score = state.productivityScore
            )

            ProgressCard(
                title = "Habit Score",
                score = state.habitScore
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {

                Text("Friends Challenge")

            }

        }

    }

}