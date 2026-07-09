package com.example.lifemap.presentation.ForgotPassword.viewmodel

data class ForgotPasswordUiState(
    val email: String = "",
    var emailError: String? = null,
    val resetSuccess: Boolean = false
)