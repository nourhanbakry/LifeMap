package com.example.lifemap.domain.repoInterface

import com.example.lifemap.domain.entity.User

interface AuthRepository {

    suspend fun signUp(
        fullName: String,
        email: String,
        password: String
    ): Result<User>

    suspend fun login(
        email: String,
        password: String
    ): Result<User>

    suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit>

    suspend fun logout()

    suspend fun saveUserLocally(user: User)

    suspend fun isUserLoggedIn(): Boolean

}