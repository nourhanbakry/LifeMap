package com.example.lifemap.presentation.EditTask.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
<<<<<<< HEAD
import androidx.compose.foundation.layout.imePadding
=======
<<<<<<< HEAD
import androidx.compose.foundation.layout.imePadding
=======
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lifemap.presentation.EditTask.viewmodel.EditTaskEvent
import com.example.lifemap.presentation.EditTask.viewmodel.EditTaskViewModel
import com.example.lifemap.presentation.common.components.CategorySelector
import com.example.lifemap.presentation.common.components.PrioritySelector
import com.example.lifemap.presentation.common.components.SubtasksSection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EditTaskScreen(navController: NavController) {

    val viewModel: EditTaskViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val dateFormat = remember { SimpleDateFormat("MM/dd/yyyy", Locale.US) }

    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSaved, state.isDeleted) {
        if (state.isSaved || state.isDeleted) {
            navController.popBackStack()
        }
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete task?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirmation = false
                    viewModel.onEvent(EditTaskEvent.DeleteClicked)
                }) {
                    Text("Delete", color = Color(0xFFEF4444))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading task...", color = Color(0xFF94A3B8))
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FC))
            .verticalScroll(rememberScrollState())
<<<<<<< HEAD
            .imePadding()
=======
<<<<<<< HEAD
            .imePadding()
=======
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // ------ Top bar ------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Edit Task",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            IconButton(onClick = { showDeleteConfirmation = true }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete task", tint = Color(0xFFEF4444))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FieldLabel("Task Title")
        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(EditTaskEvent.TitleChanged(it)) },
            isError = state.titleError != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        if (state.titleError != null) {
            Text(state.titleError ?: "", color = Color(0xFFEF4444), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel("Description")
        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onEvent(EditTaskEvent.DescriptionChanged(it)) },
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel("Category")
        CategorySelector(
            selected = state.category,
            onSelected = { viewModel.onEvent(EditTaskEvent.CategoryChanged(it)) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel("Date")
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = dateFormat.format(state.dateMillis),
                onValueChange = {},
                enabled = false,
                trailingIcon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Pick date") },
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable {
                        showDatePicker(context, state.dateMillis) { millis ->
                            viewModel.onEvent(EditTaskEvent.DateChanged(millis))
                        }
                    }
            )
        }
<<<<<<< HEAD
=======
=======
        OutlinedTextField(
            value = dateFormat.format(state.dateMillis),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Pick date") },
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePicker(context, state.dateMillis) { millis ->
                        viewModel.onEvent(EditTaskEvent.DateChanged(millis))
                    }
                }
        )
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                FieldLabel("Start Time")
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.startTime,
                        onValueChange = {},
                        enabled = false,
                        trailingIcon = { Icon(Icons.Filled.Schedule, contentDescription = "Pick start time") },
                        shape = RoundedCornerShape(12.dp),
                        colors = fieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                showTimePicker(context) { time ->
                                    viewModel.onEvent(EditTaskEvent.StartTimeChanged(time))
                                }
                            }
                    )
                }
<<<<<<< HEAD
=======
=======
                OutlinedTextField(
                    value = state.startTime,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Filled.Schedule, contentDescription = "Pick start time") },
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showTimePicker(context) { time ->
                                viewModel.onEvent(EditTaskEvent.StartTimeChanged(time))
                            }
                        }
                )
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                FieldLabel("End Time")
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.endTime,
                        onValueChange = {},
                        enabled = false,
                        trailingIcon = { Icon(Icons.Filled.Schedule, contentDescription = "Pick end time") },
                        shape = RoundedCornerShape(12.dp),
                        colors = fieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                showTimePicker(context) { time ->
                                    viewModel.onEvent(EditTaskEvent.EndTimeChanged(time))
                                }
                            }
                    )
                }
<<<<<<< HEAD
=======
=======
                OutlinedTextField(
                    value = state.endTime,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Filled.Schedule, contentDescription = "Pick end time") },
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showTimePicker(context) { time ->
                                viewModel.onEvent(EditTaskEvent.EndTimeChanged(time))
                            }
                        }
                )
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel("Priority")
        PrioritySelector(
            selected = state.priority,
            onSelected = { viewModel.onEvent(EditTaskEvent.PriorityChanged(it)) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel("Subtasks")
        SubtasksSection(
            subtasks = state.subtasks,
            newSubtaskText = state.newSubtaskText,
            onNewSubtaskTextChange = { viewModel.onEvent(EditTaskEvent.NewSubtaskTextChanged(it)) },
            onAddSubtask = { viewModel.onEvent(EditTaskEvent.AddSubtaskClicked) },
            onToggleSubtask = { viewModel.onEvent(EditTaskEvent.ToggleSubtaskClicked(it)) },
            onRemoveSubtask = { viewModel.onEvent(EditTaskEvent.RemoveSubtaskClicked(it)) }
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.error ?: "", color = Color(0xFFEF4444), fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            enabled = !state.isSaving,
            onClick = { viewModel.onEvent(EditTaskEvent.UpdateClicked) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFF3730A3),
                            1.0f to Color(0xFFBE185D)
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Box(modifier = Modifier.padding(vertical = 14.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = if (state.isSaving) "Saving..." else "Update Task",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color(0xFF64748B),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFFBE185D),
    unfocusedBorderColor = Color(0xFFE2E8F0),
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
    errorBorderColor = Color(0xFFEF4444),
    disabledBorderColor = Color(0xFFE2E8F0),
    disabledContainerColor = Color.White,
    disabledTextColor = Color(0xFF0F172A),
    disabledTrailingIconColor = Color(0xFF64748B),
    disabledLabelColor = Color(0xFF64748B)
<<<<<<< HEAD
=======
=======
    errorBorderColor = Color(0xFFEF4444)
>>>>>>> 2029bc243a7de2b70403f1f79fcda4d28253bcf9
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
)

private fun showDatePicker(
    context: android.content.Context,
    currentMillis: Long,
    onDatePicked: (Long) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentMillis

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val picked = Calendar.getInstance()
            picked.set(year, month, dayOfMonth, 0, 0, 0)
            onDatePicked(picked.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

private fun showTimePicker(
    context: android.content.Context,
    onTimePicked: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    TimePickerDialog(
        context,
        { _, hour, minute ->
            val picked = Calendar.getInstance()
            picked.set(Calendar.HOUR_OF_DAY, hour)
            picked.set(Calendar.MINUTE, minute)

            val format = SimpleDateFormat("hh:mm a", Locale.US)
            onTimePicked(format.format(picked.time))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
<<<<<<< HEAD
}
=======
}
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
