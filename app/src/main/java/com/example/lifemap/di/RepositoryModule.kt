package com.example.lifemap.di

import com.example.lifemap.data.local.UserPreferences
import com.example.lifemap.data.remote.AuthRemoteDataSource
<<<<<<< HEAD
import com.example.lifemap.data.remote.HabitRemoteDataSource
import com.example.lifemap.data.remote.TaskRemoteDataSource
import com.example.lifemap.data.repoImpl.AuthRepositoryImpl
import com.example.lifemap.data.repoImpl.HabitRepositoryImpl
import com.example.lifemap.data.repoImpl.TaskRepositoryImpl
import com.example.lifemap.domain.repoInterface.AuthRepository
import com.example.lifemap.domain.repoInterface.HabitRepository
=======
import com.example.lifemap.data.remote.TaskRemoteDataSource
import com.example.lifemap.data.repoImpl.AuthRepositoryImpl
import com.example.lifemap.data.repoImpl.TaskRepositoryImpl
import com.example.lifemap.domain.repoInterface.AuthRepository
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
import com.example.lifemap.domain.repoInterface.TaskRepository
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

    @Provides
    @Singleton
    fun provideTaskRepository(
        remote: TaskRemoteDataSource
    ): TaskRepository {

        return TaskRepositoryImpl(remote)

    }

<<<<<<< HEAD
    @Provides
    @Singleton
    fun provideHabitRepository(
        remote: HabitRemoteDataSource
    ): HabitRepository {

        return HabitRepositoryImpl(remote)

    }

}
=======
}
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
