package com.kenshi.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.kenshi.presentation.R
import com.kenshi.presentation.adapter.BlogSearchAdapter
import com.kenshi.presentation.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentBlogSearchListBinding
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class BlogSearchListFragment :
    BaseFragment<FragmentBlogSearchListBinding>(R.layout.fragment_blog_search_list) {

    private val viewModel by activityViewModels<SearchViewModel>()

    private val blogSearchAdapter by lazy {
        BlogSearchAdapter()
    }

    override fun getViewBinding() = FragmentBlogSearchListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        binding.rvBlogSearch.adapter = blogSearchAdapter.withLoadStateFooter(
            footer = SearchLoadStateAdapter(
                blogSearchAdapter::retry
            )
        )
    }

    private fun initListener() {

    }

    private fun initObserver() {
        repeatOnStarted {
            viewModel.searchBlogs.collectLatest {
                blogSearchAdapter.submitData(it)
            }
        }
    }
}