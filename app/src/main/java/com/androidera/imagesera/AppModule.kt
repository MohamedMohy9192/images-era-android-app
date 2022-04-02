package com.androidera.imagesera

import com.androidera.imagesera.network.BASE_URL
import com.androidera.imagesera.network.UnsplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Modules are containers for dependencies that live a specific
 * amount of time, in app module we will define dependencies that just live as long as
 * application does
 * In this app module we need to give dagger a blueprint how it can construct the dependencies
 * that we want to inject
 */
@Module
@InstallIn(SingletonComponent::class) // Scope these dependencies we declare in this module to the live as long the application
object AppModule {

    /**
     *
     * When we annotated this method with @Provides
     * we turn this into a method tells dagger how to create an object in
     * this case Retrofit object
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApiService {
        val service: UnsplashApiService by lazy { retrofit.create(UnsplashApiService::class.java) }
        return service
    }
}