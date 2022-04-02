package com.androidera.imagesera

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.androidera.imagesera.network.UnsplashApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val service: UnsplashApiService
) {

    fun searchUnsplashPhotos(searchInput: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(service, searchInput) }
        ).liveData
}