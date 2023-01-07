package com.hence.di

import com.hence.data.repository.AfreecaRepositoryImpl
import com.hence.domain.repository.AfreecaRepository
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
    abstract fun bindAfreecaRepository(
        impl: AfreecaRepositoryImpl
    ): AfreecaRepository
}