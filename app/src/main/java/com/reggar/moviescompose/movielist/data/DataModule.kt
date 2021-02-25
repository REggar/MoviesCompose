package com.reggar.moviescompose.movielist.data

import com.reggar.moviescompose.common.data.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideClock() = Clock.systemUTC()

    @Provides
    @Singleton
    fun provideRetrofitClient(environment: Environment) = Retrofit.Builder()
        .baseUrl(environment.url)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit) = retrofit.create(MovieApi::class.java)
}
