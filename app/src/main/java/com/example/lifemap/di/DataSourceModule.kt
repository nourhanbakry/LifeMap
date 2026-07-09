package com.example.lifemap.di

import com.example.lifemap.data.remote.AuthRemoteDataSource
import com.example.lifemap.data.remote.AuthRemoteDataSourceImpl
<<<<<<< HEAD
import com.example.lifemap.data.remote.TaskRemoteDataSource
import com.example.lifemap.data.remote.TaskRemoteDataSourceImpl
=======
>>>>>>> 9b903e2bfd2f30a5b37e3d0d8a16ae1be7aaa810
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

<<<<<<< HEAD
    @Binds
    @Singleton
    abstract fun bindTaskRemoteDataSource(
        impl: TaskRemoteDataSourceImpl
    ): TaskRemoteDataSource

}
=======
}
>>>>>>> 9b903e2bfd2f30a5b37e3d0d8a16ae1be7aaa810
