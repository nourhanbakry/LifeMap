package com.example.lifemap.presentation.authentication.signup.viewmodel

sealed interface SignUpUiEvent {

    data object NavigateToHome : SignUpUiEvent

    data class ShowSnackbar(
        val message: String
    ) : SignUpUiEvent
}