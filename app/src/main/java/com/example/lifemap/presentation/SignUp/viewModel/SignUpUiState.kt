package com.example.lifemap.presentation.SignUp.viewModel

data class SignUpUiState(

    val fullName: String = "",

    val email: String = "",

    val password: String = "",

    val fullNameError: String? = null,

    val emailError: String? = null,

    val passwordError: String? = null,


    val isLoading: Boolean = false

)