package com.example.lifemap.presentation.authentication.signup.viewmodel

sealed interface SignUpEvent {
    data class FullNameChanged(
        val fullName: String
    ) : SignUpEvent

    data class EmailChanged(
        val email: String
    ) : SignUpEvent

    data class PasswordChanged(
        val password: String
    ) : SignUpEvent

    data object SignUpClicked : SignUpEvent

}