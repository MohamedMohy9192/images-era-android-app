package com.androidera.imagesera

import androidx.paging.PagingSource
import com.androidera.imagesera.model.UnsplashPhoto
import com.androidera.imagesera.network.UnsplashApiService
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

/**
 * PagingSource is a class that knows how to load data from the API
 * and then turn it into pages
 *
 * @param service instance of retrofit API interface
 * @param searchQuery user search input
 *
 *  Int : Key we want to use to distinguish different
 *  pages, in most cases will be an int because we distinguish between pages by the number
 *  UnsplashPhoto: the type of data we want to use to fill this pages
 */
class UnsplashPagingSource(
    private val service: UnsplashApiService,
    private val searchQuery: String
) : PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        // The page we are currently on
        val pageIndex = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val searchResponse = service.searchPhotos(
                searchQuery,
                pageNumber = pageIndex,
                numberOfItemsPerPage = params.loadSize
            )
            val unsplashPhotos = searchResponse.unsplashPhotos
            // if thing went well return one page of result
            LoadResult.Page(
                data = unsplashPhotos, // the data we want to put into the page
                // the index number of the previous page, return null if we are on the first page
                // and no previous page
                prevKey = if (pageIndex == UNSPLASH_STARTING_PAGE_INDEX) null else pageIndex.minus(1),
                // the index number of the next page, return null if we are on the last page cause
                // no more photos
                nextKey = if (unsplashPhotos.isEmpty()) null else pageIndex.plus(1)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}

