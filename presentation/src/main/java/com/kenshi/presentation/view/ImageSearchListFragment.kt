package com.kenshi.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.kenshi.presentation.R
import com.kenshi.presentation.adapter.ImageSearchAdapter
import com.kenshi.presentation.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentImageSearchListBinding
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.viewmodel.SearchViewModel
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
        imageSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = imageSearchAdapter.itemCount < 1 &&
                    loadState.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached

            binding.tvNoResult.isVisible = isListEmpty
            binding.rvImageSearch.isVisible = !isListEmpty
            binding.pbImageSearch.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            launch {
                viewModel.searchImages.collectLatest {
                    imageSearchAdapter.submitData(it)
                }
            }
        }
    }
}