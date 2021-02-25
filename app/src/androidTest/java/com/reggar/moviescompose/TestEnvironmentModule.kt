package com.reggar.moviescompose

import com.reggar.moviescompose.common.data.Environment
import com.reggar.moviescompose.common.data.EnvironmentModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [EnvironmentModule::class]
)
class TestEnvironmentModule {

    @Provides
    fun provideEnvironment() = Environment("http://localhost:8080/")
}
