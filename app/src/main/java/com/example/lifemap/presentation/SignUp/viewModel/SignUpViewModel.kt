package com.example.lifemap.presentation.authentication.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifemap.domain.repoInterface.AuthRepository
import com.example.lifemap.presentation.SignUp.viewModel.SignUpUiState
import com.example.lifemap.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<SignUpUiEvent>()
    val event = _event.asSharedFlow()

    fun onEvent(event: SignUpEvent) {

        when (event) {

            is SignUpEvent.FullNameChanged -> {
                _uiState.update {
                    it.copy(
                        fullName = event.fullName,
                        fullNameError = null
                    )
                }
            }

            is SignUpEvent.EmailChanged -> {
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = null
                    )
                }
            }

            is SignUpEvent.PasswordChanged -> {
                _uiState.update {
                    it.copy(
                        password = event.password,
                        passwordError = null
                    )
                }
            }


            SignUpEvent.SignUpClicked -> {
                signUp()
            }
        }
    }

    private fun signUp() {

        val state = _uiState.value

        val nameError = ValidationUtils.validateName(state.fullName)
        val emailError = ValidationUtils.validateEmail(state.email)
        val passwordError = ValidationUtils.validatePassword(state.password)


        _uiState.update {
            it.copy(
                fullNameError = nameError,
                emailError = emailError,
                passwordError = passwordError,)
        }

        if (
            nameError != null ||
            emailError != null ||
            passwordError != null
        ) return

        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true)
            }

            val result = repository.signUp(
                fullName = state.fullName,
                email = state.email,
                password = state.password
            )

            result.onSuccess {

                _uiState.update {
                    it.copy(isLoading = false)
                }

                _event.emit(SignUpUiEvent.NavigateToHome)
            }

            result.onFailure {

                _uiState.update {
                    it.copy(isLoading = false)
                }

                _event.emit(
                    SignUpUiEvent.ShowSnackbar(
                        it.message ?: "Something went wrong"
                    )
                )
            }
        }
    }
}