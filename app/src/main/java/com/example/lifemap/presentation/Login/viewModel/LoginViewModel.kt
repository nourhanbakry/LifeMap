package com.example.lifemap.presentation.Login.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lifemap.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {

            is LoginEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email,
                    emailError = null
                )
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            LoginEvent.TogglePasswordVisibility -> {
                _uiState.value = _uiState.value.copy(
                    isPasswordVisible = !_uiState.value.isPasswordVisible
                )
            }

            LoginEvent.LoginClicked -> login()
        }
    }

    private fun login() {

        val state = _uiState.value

        val emailError = ValidationUtils.validateEmail(state.email)
        val passwordError = ValidationUtils.validatePassword(state.password)

        _uiState.value = state.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        if (emailError == null && passwordError == null) {
            _uiState.value = _uiState.value.copy(
                loginSuccess = true
            )
        }
    }
}