package com.kenshi.data.di

import com.kenshi.data.source.local.SearchLocalDataSource
import com.kenshi.data.source.local.SearchLocalDataSourceImpl
import com.kenshi.data.source.remote.SearchRemoteDataSource
import com.kenshi.data.source.remote.SearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindSearchRemoteDataSource(searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl): SearchRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindSearchLocalDataSource(searchLocalDataSourceImpl: SearchLocalDataSourceImpl): SearchLocalDataSource
}