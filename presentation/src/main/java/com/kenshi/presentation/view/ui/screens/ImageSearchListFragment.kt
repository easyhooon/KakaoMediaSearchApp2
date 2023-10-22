package com.kenshi.presentation.view.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.kenshi.presentation.R
import com.kenshi.presentation.SearchViewModel
import com.kenshi.presentation.databinding.FragmentImageSearchListBinding
import com.kenshi.presentation.extensions.addDivider
import com.kenshi.presentation.extensions.repeatOnStarted
import com.kenshi.presentation.extensions.safeNavigate
import com.kenshi.presentation.view.adapter.ImageSearchAdapter
import com.kenshi.presentation.view.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
            adapter = imageSearchAdapter.withLoadStateFooter(
                footer = SearchLoadStateAdapter(
                    imageSearchAdapter::retry
                )
            )
            addDivider(R.color.gray_300)
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

        binding.btnImageSearchRetry.setOnClickListener {
            viewModel.refresh()
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
                imageSearchAdapter.loadStateFlow.collectLatest { loadStates ->
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

            launch {
                viewModel.refreshClickEvent.collect {
                    imageSearchAdapter.retry()
                }
            }
        }
    }
}