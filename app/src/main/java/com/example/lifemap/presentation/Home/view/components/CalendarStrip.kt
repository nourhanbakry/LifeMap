package com.example.lifemap.presentation.Home.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

// Width reserved for a single day column (used both for layout and for
// calculating how far to scroll to bring the selected day into view).
private val DAY_ITEM_WIDTH = 48.dp

@Composable
fun CalendarStrip(
    monthLabel: String,
    weekDatesMillis: List<Long>,
    selectedDateMillis: Long,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDaySelected: (Long) -> Unit
) {
    val dayNameFormat = SimpleDateFormat("EEE", Locale.US)
    val dayNumberFormat = SimpleDateFormat("d", Locale.US)

    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    // Whenever the visible days or the selected day change, scroll so the
    // selected day sits a couple of days in from the left edge instead of
    // being flush against it or hidden off-screen.
    LaunchedEffect(weekDatesMillis, selectedDateMillis) {
        val index = weekDatesMillis.indexOfFirst { isSameDay(it, selectedDateMillis) }
        if (index >= 0) {
            val itemWidthPx = with(density) { DAY_ITEM_WIDTH.toPx() }
            val target = ((index - 2).coerceAtLeast(0) * itemWidthPx).toInt()
            scrollState.animateScrollTo(target)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousMonth) {
                Icon(Icons.Filled.ChevronLeft, contentDescription = "Previous month")
            }

            Text(
                text = monthLabel,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF0F172A)
            )

            IconButton(onClick = onNextMonth) {
                Icon(Icons.Filled.ChevronRight, contentDescription = "Next month")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            weekDatesMillis.forEach { dateMillis ->

                val isSelected = isSameDay(dateMillis, selectedDateMillis)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(DAY_ITEM_WIDTH)
                        .clickable { onDaySelected(dateMillis) }
                ) {
                    Text(
                        text = dayNameFormat.format(dateMillis).uppercase(),
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .size(36.dp)
                            .background(
                                color = if (isSelected) Color(0xFF6D28D9) else Color.Transparent,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dayNumberFormat.format(dateMillis),
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else Color(0xFF0F172A)
                        )
                    }
                }
            }
        }
    }
}

private fun isSameDay(a: Long, b: Long): Boolean {

    val format = SimpleDateFormat("yyyyMMdd", Locale.US)

    return format.format(a) == format.format(b)
}
