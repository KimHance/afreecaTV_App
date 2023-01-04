package com.hence.di

import com.hence.data.source.datasource.AfreecaDataSource
import com.hence.data.source.datasource.impl.AfreecaDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAfreecaDataSource(
        impl: AfreecaDataSourceImpl
    ): AfreecaDataSource
}