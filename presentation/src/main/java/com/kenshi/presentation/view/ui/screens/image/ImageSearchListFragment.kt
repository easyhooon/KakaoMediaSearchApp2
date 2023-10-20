package com.kenshi.presentation.view.ui.screens.image

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.kenshi.presentation.R
import com.kenshi.presentation.databinding.FragmentImageSearchListBinding
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.util.safeNavigate
import com.kenshi.presentation.view.adapter.ImageSearchAdapter
import com.kenshi.presentation.view.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.view.base.BaseFragment
import com.kenshi.presentation.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageSearchListFragment :
    BaseFragment<FragmentImageSearchListBinding>(R.layout.fragment_image_search_list) {

    private val viewModel by activityViewModels<SearchViewModel>()

    private val imageSearchAdapter by lazy {
        ImageSearchAdapter()
    }

    override fun getViewBinding() = FragmentImageSearchListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        binding.rvImageSearch.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = imageSearchAdapter.withLoadStateFooter(
                footer = SearchLoadStateAdapter(
                    imageSearchAdapter::retry
                )
            )
        }
    }

    private fun initListener() {
        imageSearchAdapter.setOnItemClickListener { url ->
            val action =
                ImageSearchListFragmentDirections.actionImageSearchListFragmentToSearchDetailFragment(
                    url
                )
            findNavController().safeNavigate(action)
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            launch {
                viewModel.searchImages.collectLatest {
                    imageSearchAdapter.submitData(it)
                }
            }

            launch {
                imageSearchAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .collect { loadStates ->
                        val loadState = loadStates.source

                        val isListEmpty = imageSearchAdapter.itemCount < 1 &&
                                loadState.refresh is LoadState.NotLoading &&
                                loadState.append.endOfPaginationReached

                        val isError = loadState.refresh is LoadState.Error

                        binding.apply {
                            pbImageSearch.isVisible = loadState.refresh is LoadState.Loading
                            tvImageSearchNoResult.isVisible = isListEmpty
                            rvImageSearch.isVisible = !isListEmpty

                            tvImageSearchError.isVisible = isError
                            btnImageSearchRetry.isVisible = isError
                        }
                    }
            }
        }
    }
}