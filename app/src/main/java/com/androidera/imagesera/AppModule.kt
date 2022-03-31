package com.androidera.imagesera

import com.androidera.imagesera.network.BASE_URL
import com.androidera.imagesera.network.UnsplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * a special type of class that gives dagger instruction
 * on how to create the objects we need
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * When we annotated this method with @Provides
     * we turn this into a method tells dagger how to create an object in
     * this case Retrofit object
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApiService {
        val service: UnsplashApiService by lazy { retrofit.create(UnsplashApiService::class.java) }
        return service
    }
}