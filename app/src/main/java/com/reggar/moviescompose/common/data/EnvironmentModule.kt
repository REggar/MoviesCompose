package com.reggar.moviescompose.common.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EnvironmentModule {

    @Provides
    fun provideEnvironment() = Environment("https://movies-sample.herokuapp.com/")
}
