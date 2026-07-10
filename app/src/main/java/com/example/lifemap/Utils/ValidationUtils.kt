package com.example.lifemap.utils

import android.util.Patterns

object ValidationUtils {

    fun validateName(name: String): String? {

        return when {
            name.isBlank() -> "Name is required"
            name.length < 3 -> "Name must be at least 3 characters"
            else -> null
        }

    }

    fun validateEmail(email: String): String? {

        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Enter a valid email"
            else -> null
        }

    }

    fun validatePassword(password: String): String? {

        return when {
            password.isBlank() -> "Password is required"

            password.length < 6 ->
                "Password must be at least 6 characters"

            !isStrongPassword(password) ->
                "Password must contain upper, lower, number and symbol"

            else -> null
        }

    }


    private fun isStrongPassword(password: String): Boolean {

        return password.matches(
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$")
        )

    }

}