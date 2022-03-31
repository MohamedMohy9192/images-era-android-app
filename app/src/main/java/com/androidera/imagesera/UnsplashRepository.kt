package com.androidera.imagesera

import com.androidera.imagesera.network.UnsplashApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val service: UnsplashApiService
) {
}