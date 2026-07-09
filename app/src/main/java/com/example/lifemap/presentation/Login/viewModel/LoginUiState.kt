package com.example.lifemap.presentation.Login.viewmodel

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,

    val isPasswordVisible: Boolean = false,

    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)