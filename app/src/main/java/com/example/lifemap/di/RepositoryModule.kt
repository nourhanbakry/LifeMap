package com.example.lifemap.di

import com.example.lifemap.data.local.UserPreferences
import com.example.lifemap.data.remote.AuthRemoteDataSource
import com.example.lifemap.data.repoImpl.AuthRepositoryImpl
import com.example.lifemap.domain.repoInterface.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        remote: AuthRemoteDataSource,
        local: UserPreferences
    ): AuthRepository {

        return AuthRepositoryImpl(remote, local)

    }

}