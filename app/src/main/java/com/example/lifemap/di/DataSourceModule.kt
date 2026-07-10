package com.example.lifemap.di

import com.example.lifemap.data.remote.AuthRemoteDataSource
import com.example.lifemap.data.remote.AuthRemoteDataSourceImpl
<<<<<<< HEAD
import com.example.lifemap.data.remote.HabitRemoteDataSource
import com.example.lifemap.data.remote.HabitRemoteDataSourceImpl
=======
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
import com.example.lifemap.data.remote.TaskRemoteDataSource
import com.example.lifemap.data.remote.TaskRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        impl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindTaskRemoteDataSource(
        impl: TaskRemoteDataSourceImpl
    ): TaskRemoteDataSource

<<<<<<< HEAD
    @Binds
    @Singleton
    abstract fun bindHabitRemoteDataSource(
        impl: HabitRemoteDataSourceImpl
    ): HabitRemoteDataSource

}
=======
}
>>>>>>> e4c6a877bfa3a3c64ec43f0d145666760c092073
