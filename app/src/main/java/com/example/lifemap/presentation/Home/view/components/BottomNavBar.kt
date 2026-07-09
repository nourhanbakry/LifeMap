package com.example.lifemap.presentation.Home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class NavItem(
    val label: String,
    val icon: ImageVector
)

private val navItems = listOf(
    NavItem("Home", Icons.Filled.Home),
    NavItem("Habits", Icons.Filled.Repeat),
    NavItem("Progress", Icons.Filled.BarChart),
    NavItem("Settings", Icons.Filled.Settings)
)

@Composable
fun BottomNavBar(
    selectedLabel: String = "Home",
    onItemClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        navItems.forEach { item ->

            val isSelected = item.label == selectedLabel
            val tint = if (isSelected) Color(0xFF6D28D9) else Color(0xFF94A3B8)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onItemClick(item.label) }
            ) {
                Icon(imageVector = item.icon, contentDescription = item.label, tint = tint)
                Text(text = item.label, fontSize = 11.sp, color = tint)
            }
        }
    }
}
