package com.example.lifemap.presentation.Login.view

import android.util.Log
import android.util.Patterns
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lifemap.R
import com.example.lifemap.presentation.Login.viewmodel.LoginEvent
import com.example.lifemap.presentation.Login.viewmodel.LoginViewModel
import com.example.lifemap.presentation.Navigation.Routes
import com.example.lifemap.presentation.authentication.signup.viewmodel.SignUpEvent
import okhttp3.Route

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.LOGIN) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFE)) // Clean background color matching the design
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start // Left-aligned header items
    ) {

        // Top flexible spacer for balanced vertical alignment
        Spacer(modifier = Modifier.weight(1f))

        // ------ Header Section ------
        Text(
            text = stringResource(R.string.login),
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFF0F172A)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.welcome_back_to_lifemap),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF64748B),
                fontFamily = FontFamily.SansSerif
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ------ Email Input Field ------
        Text(
            text = stringResource(R.string.email),
            fontSize = 14.sp,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(LoginEvent.EmailChanged(it))
            },
            placeholder = { Text(text = stringResource(R.string.your_email_com), color = Color(0xFFCBD5E1)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = stringResource(R.string.email_icon),
                    tint = if (state.emailError != null) Color(0xFFEF4444) else Color(0xFF94A3B8)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = state.emailError != null, // Signals visual error state to the text field
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFBE185D),       // Border color when focused
                unfocusedBorderColor = Color(0xFFE2E8F0),     // Default border color
                focusedContainerColor = Color.White,          // Background color when focused
                unfocusedContainerColor = Color.White,        // Default background color
                errorBorderColor = Color(0xFFEF4444)          // Soft red error border
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        // Dynamic error message for Email
        if (state.emailError != null) {
            Text(
                text = state.emailError ?: "",
                color = Color(0xFFEF4444),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ------ Password Input Field ------
        Text(
            text = stringResource(R.string.password),
            fontSize = 14.sp,
            color = Color(0xFF94A3B8),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(LoginEvent.PasswordChanged(it))
            },
            placeholder = { Text(text = stringResource(R.string.password_placeholder), color = Color(0xFFCBD5E1)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = stringResource(R.string.password_icon),
                    tint = if (state.passwordError != null) Color(0xFFEF4444) else Color(0xFF94A3B8)
                )
            },
            trailingIcon = {
                // Interactive visibility toggle button
                IconButton(onClick = { viewModel.onEvent(LoginEvent.TogglePasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (state.isPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                            R.string.show_password
                        ),
                        tint = Color(0xFF94A3B8)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = state.passwordError != null, // Signals visual error state to the text field
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFBE185D),     // Border color when focused
                unfocusedBorderColor = Color(0xFFE2E8F0),   // Default border color
                focusedContainerColor = Color.White,        // Background color when focused
                unfocusedContainerColor = Color.White,      // Default background color
                errorBorderColor = Color(0xFFEF4444)        // Soft red error border
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        // Dynamic error message for Password
        if (state.passwordError != null) {
            Text(
                text = state.passwordError ?: "",
                color = Color(0xFFEF4444),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ------ Forgot Password Link ------
        Text(
            text = stringResource(R.string.forgot_password),
            fontSize = 14.sp,
            color = Color(0xFF3B82F6), // Accent blue link color
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    navController.navigate(Routes.FORGOT_PASSWORD)  // Navigate to Forgot Password screen
                }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ------ Gradient Login Button ------
        Button(
            enabled = !state.isLoading,
            onClick = {
                viewModel.onEvent(LoginEvent.LoginClicked)
            },
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
            Box(
                modifier = Modifier.padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        // Fixed spacing right under the login button
        Spacer(modifier = Modifier.height(32.dp))

        // ------ Footer Section (Navigation to SignUp) ------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.don_t_have_an_account),
                color = Color(0xFF64748B),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(R.string.sign_up),
                color = Color(0xFF3B82F6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.SIGNUP)
                }
            )
        }

        // Bottom flexible spacer to settle layouts safely on smaller devices
        Spacer(modifier = Modifier.weight(1f))
    }
}