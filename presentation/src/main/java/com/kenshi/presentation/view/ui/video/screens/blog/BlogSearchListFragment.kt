package com.kenshi.presentation.view.ui.video.screens.blog

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.kenshi.presentation.R
import com.kenshi.presentation.view.adapter.BlogSearchAdapter
import com.kenshi.presentation.view.adapter.SearchLoadStateAdapter
import com.kenshi.presentation.view.base.BaseFragment
import com.kenshi.presentation.databinding.FragmentBlogSearchListBinding
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.util.safeNavigate
import com.kenshi.presentation.view.ui.SearchViewModel
import com.kenshi.presentation.view.ui.blog.BlogSearchListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


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
        binding.rvBlogSearch.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = blogSearchAdapter.withLoadStateFooter(
                footer = SearchLoadStateAdapter(
                    blogSearchAdapter::retry
                )
            )
        }
    }

    private fun initListener() {
        blogSearchAdapter.apply {
            addLoadStateListener { combinedLoadStates ->
                val loadState = combinedLoadStates.source
                val isListEmpty = blogSearchAdapter.itemCount < 1 &&
                        loadState.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached

                binding.tvNoResult.isVisible = isListEmpty
                binding.rvBlogSearch.isVisible = !isListEmpty
                binding.pbBlogSearch.isVisible = loadState.refresh is LoadState.Loading
            }
            setOnItemClickListener { url->
                val action =
                    BlogSearchListFragmentDirections.actionBlogSearchListFragmentToSearchDetailFragment(
                        url
                    )
                findNavController().safeNavigate(action)
            }
        }
    }

    private fun initObserver() {
        repeatOnStarted {
            launch {
                viewModel.searchBlogs.collectLatest {
                    blogSearchAdapter.submitData(it)
                }
            }
        }
    }
}