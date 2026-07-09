package com.example.lifemap.di

import com.example.lifemap.data.local.UserPreferences
import com.example.lifemap.data.remote.AuthRemoteDataSource
<<<<<<< HEAD
import com.example.lifemap.data.remote.TaskRemoteDataSource
import com.example.lifemap.data.repoImpl.AuthRepositoryImpl
import com.example.lifemap.data.repoImpl.TaskRepositoryImpl
import com.example.lifemap.domain.repoInterface.AuthRepository
import com.example.lifemap.domain.repoInterface.TaskRepository
=======
import com.example.lifemap.data.repoImpl.AuthRepositoryImpl
import com.example.lifemap.domain.repoInterface.AuthRepository
>>>>>>> 9b903e2bfd2f30a5b37e3d0d8a16ae1be7aaa810
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

<<<<<<< HEAD
    @Provides
    @Singleton
    fun provideTaskRepository(
        remote: TaskRemoteDataSource
    ): TaskRepository {

        return TaskRepositoryImpl(remote)

    }

}
=======
}
>>>>>>> 9b903e2bfd2f30a5b37e3d0d8a16ae1be7aaa810
