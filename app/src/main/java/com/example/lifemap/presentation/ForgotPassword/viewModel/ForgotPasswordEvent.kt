package com.example.lifemap.presentation.ForgotPassword.viewmodel

sealed class ForgotPasswordEvent {

    data class EmailChanged(
        val email: String
    ) : ForgotPasswordEvent()

    data object SendResetClicked : ForgotPasswordEvent()
}