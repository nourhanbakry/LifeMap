package com.example.lifemap.di

import com.example.lifemap.data.remote.AuthRemoteDataSource
import com.example.lifemap.data.remote.AuthRemoteDataSourceImpl
import com.example.lifemap.data.remote.HabitRemoteDataSource
import com.example.lifemap.data.remote.HabitRemoteDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindHabitRemoteDataSource(
        impl: HabitRemoteDataSourceImpl
    ): HabitRemoteDataSource

}