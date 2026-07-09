package com.example.lifemap.data.repoImpl

import com.example.lifemap.data.local.UserPreferences
import com.example.lifemap.data.remote.AuthRemoteDataSource
import com.example.lifemap.domain.entity.User
import com.example.lifemap.domain.repoInterface.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val local: UserPreferences
) : AuthRepository {

    override suspend fun signUp(
        fullName: String,
        email: String,
        password: String
    ): Result<User> {

        val result = remote.signUp(
            fullName,
            email,
            password
        )

        result.onSuccess {
            local.saveUser(it)
        }

        return result
    }



    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {

        val result = remote.login(
            email,
            password
        )

        result.onSuccess {
            local.saveUser(it)
        }

        return result
    }


    override suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit> {

        return remote.sendPasswordResetEmail(email)

    }


    override suspend fun logout() {

        remote.logout()

        local.clearUser()

    }


    override suspend fun saveUserLocally(user: User) {

        local.saveUser(user)

    }

    override suspend fun isUserLoggedIn(): Boolean {

        return local.isLoggedIn()

    }

}