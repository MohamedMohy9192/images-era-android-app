package com.androidera.imagesera.network

import com.androidera.imagesera.BuildConfig
import com.androidera.imagesera.model.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

internal const val BASE_URL = "https://api.unsplash.com/"
private const val UNSPLASH_ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY

interface UnsplashApiService {
    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID $UNSPLASH_ACCESS_KEY"
    )
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") numberOfItemsPerPage: Int
    ): UnsplashResponse
}