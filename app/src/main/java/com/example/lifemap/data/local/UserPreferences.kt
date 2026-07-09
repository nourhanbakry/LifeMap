package com.example.lifemap.data.local

import com.example.lifemap.domain.entity.User

interface UserPreferences {

    suspend fun saveUser(user: User)

    suspend fun clearUser()

    suspend fun getUser(): User?

    suspend fun isLoggedIn(): Boolean
}