package com.example.lifemap.presentation.ForgotPassword.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lifemap.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun onEvent(event: ForgotPasswordEvent) {

        when (event) {

            is ForgotPasswordEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email,
                    emailError = ValidationUtils.validateEmail(event.email)
                )
            }

            ForgotPasswordEvent.SendResetClicked -> {

                val emailError = ValidationUtils.validateEmail(_uiState.value.email)

                _uiState.value = _uiState.value.copy(
                    emailError = emailError
                )

                if (emailError == null) {
                    _uiState.value = _uiState.value.copy(
                        resetSuccess = true
                    )
                }
            }
        }
    }
}