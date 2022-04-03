package com.androidera.imagesera

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.androidera.imagesera.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // we need to annotate the fragment with this annotation to inject dependencies and also the parent activity
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val unsplashPhotoAdapter = UnsplashPhotoAdapter()

        binding.apply {
            unsplashPhotosRecyclerView.setHasFixedSize(true)
            unsplashPhotosRecyclerView.adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter(unsplashPhotoAdapter::retry),
                footer = UnsplashPhotoLoadStateAdapter(unsplashPhotoAdapter::retry)
            )
            errorMessageView.retryButton
                .setOnClickListener { unsplashPhotoAdapter.retry() }
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            unsplashPhotoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        unsplashPhotoAdapter.addLoadStateListener { combinedLoadStates ->
            with(binding) {
                unsplashPhotosProgressBar.isVisible =
                    combinedLoadStates.source.refresh is LoadState.Loading
                unsplashPhotosRecyclerView.isVisible =
                    combinedLoadStates.source.refresh is LoadState.NotLoading
                errorMessageView.root.isVisible =
                    combinedLoadStates.source.refresh is LoadState.Error
                // if no results to show for the search input
                if (combinedLoadStates.source.refresh is LoadState.NotLoading &&
                    combinedLoadStates.append.endOfPaginationReached &&
                    unsplashPhotoAdapter.itemCount < 1
                ) {
                    unsplashPhotosRecyclerView.isVisible = false
                    searchResultTextView.isVisible = true
                } else {
                    searchResultTextView.isVisible = false
                }
            }
        }
    }

    private fun setupSearchQueryTextListener(menuItem: MenuItem) {
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // reset the scroll position to the first item
                    binding.unsplashPhotosRecyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    // Close the keyboard
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        setupSearchQueryTextListener(searchItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}