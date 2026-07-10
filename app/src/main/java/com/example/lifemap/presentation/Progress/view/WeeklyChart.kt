package com.example.lifemap.presentation.Progress.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyChart(progress: List<Float>) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text("Weekly Progress")

            Spacer(modifier = Modifier.height(8.dp))

            progress.forEachIndexed { index, value ->

                Text("Day ${index + 1}: ${value.toInt()}%")

            }

        }

    }

}