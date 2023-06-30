package com.kenshi.presentation.view.ui.screens.video

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.kenshi.presentation.R
import com.kenshi.presentation.databinding.FragmentVideoSearchListBinding
import com.kenshi.presentation.util.hideKeyboard
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.util.safeNavigate
import com.kenshi.presentation.view.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.view.adapter.VideoSearchAdapter
import com.kenshi.presentation.view.base.BaseFragment
import com.kenshi.presentation.view.ui.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoSearchListFragment :
    BaseFragment<FragmentVideoSearchListBinding>(R.layout.fragment_video_search_list) {

    private val viewModel by activityViewModels<SearchViewModel>()

    private val searchVideoAdapter by lazy {
        VideoSearchAdapter()
    }

    override fun getViewBinding() = FragmentVideoSearchListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        binding.rvVideoSearch.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = searchVideoAdapter.withLoadStateFooter(
                footer = SearchLoadStateAdapter(
                    searchVideoAdapter::retry
                )
            )
        }
    }

    private fun initListener() {
        searchVideoAdapter.apply {
            addLoadStateListener { combinedLoadStates ->
                val loadState = combinedLoadStates.source
                val isListEmpty = searchVideoAdapter.itemCount < 1 &&
                        loadState.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached

                binding.tvNoResult.isVisible = isListEmpty
                binding.rvVideoSearch.isVisible = !isListEmpty
                binding.pbVideoSearch.isVisible = loadState.refresh is LoadState.Loading
            }
            setOnItemClickListener { url ->
                val action =
                    VideoSearchListFragmentDirections.actionVideoSearchListFragmentToSearchDetailFragment(
                        url
                    )
                findNavController().safeNavigate(action)
            }
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            launch {
                viewModel.searchVideos.collectLatest {
                    searchVideoAdapter.submitData(it)
                    // 동작 안함
                    requireActivity().hideKeyboard()
                }
            }
        }
    }
}