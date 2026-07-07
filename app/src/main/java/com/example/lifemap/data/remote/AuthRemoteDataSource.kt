package com.example.lifemap.data.remote

import com.example.lifemap.domain.entity.User

interface AuthRemoteDataSource {

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

}