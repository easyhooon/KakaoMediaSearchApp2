package com.kenshi.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kenshi.presentation.R
import com.kenshi.presentation.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.adapter.VideoSearchAdapter
import com.kenshi.presentation.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentVideoSearchListBinding
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class VideoSearchListFragment :
    BaseFragment<FragmentVideoSearchListBinding>(R.layout.fragment_video_search_list) {

    private val viewModel by activityViewModels<SearchViewModel>()

    private val videoSearchAdapter by lazy {
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
        binding.rvVideoSearch.adapter = videoSearchAdapter.withLoadStateFooter(
            footer = SearchLoadStateAdapter(
                videoSearchAdapter::retry
            )
        )
    }

    private fun initListener() {

    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.searchVideos.collectLatest {
                videoSearchAdapter.submitData(it)
            }
        }
    }
}