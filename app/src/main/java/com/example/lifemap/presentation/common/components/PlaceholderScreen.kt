package com.example.lifemap.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lifemap.presentation.Home.view.components.BottomNavBar
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.Navigation.navigateToBottomNavRoute

/**
 * Generic empty-state screen used by bottom-nav tabs that don't have
 * their real UI built yet (Habits, Progress, Settings, ...).
 *
 * It already wires up the BottomNavBar navigation, so whoever builds the
 * real feature only needs to replace the body content of this screen
 * (or create their own screen and swap it in Navigation.kt) without
 * touching navigation wiring.
 */
@Composable
fun PlaceholderScreen(
    title: String,
    navController: NavController,
    icon: ImageVector? = null
) {
    Scaffold(
        containerColor = Color(0xFFF8F9FC),
        bottomBar = {
            BottomNavBar(
                selectedLabel = title,
                onItemClick = { label ->
                    navController.navigateToBottomNavRoute(Routes.routeForBottomNavLabel(label))
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                if (icon != null) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color(0xFFEDE9FE), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = title, tint = Color(0xFF6D28D9))
                    }
                }

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(
                    text = "coming soon",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
