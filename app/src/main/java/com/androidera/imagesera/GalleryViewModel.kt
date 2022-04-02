package com.androidera.imagesera

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    private val _searchInput = MutableLiveData(DEFAULT_SEARCH_INPUT)

    val photos =
        _searchInput.switchMap { searchInput ->
            repository.searchUnsplashPhotos(searchInput).cachedIn(viewModelScope)
        }

    fun searchPhotos(searchInput: String) {
        _searchInput.value = searchInput
    }

    companion object {
        private const val DEFAULT_SEARCH_INPUT = "cats"
    }
}