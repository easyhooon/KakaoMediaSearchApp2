package com.kenshi.presentation.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.kenshi.presentation.R
import com.kenshi.presentation.view.base.BaseActivity
import com.kenshi.presentation.databinding.ActivitySearchViewBinding
import com.kenshi.presentation.util.Constants.SEARCH_TIME_DELAY
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.util.textChangesToFlow
import com.kenshi.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class SearchViewActivity : BaseActivity<ActivitySearchViewBinding>(R.layout.activity_search_view) {

    private val searchViewModel by viewModels<SearchViewModel>()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun getViewBinding() = ActivitySearchViewBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTabsWithNavigation()
        initObserver()
    }

    private fun initObserver() {
        // 하나의 flow 에서 수명 주기 인식 수집을 진행 하기만 하면 되는 경우엔 Flow.flowWithLifecycle 메서드를 사용하면 됨
        repeatOnStarted {
            val editTextFlow = binding.etSearch.textChangesToFlow()

            editTextFlow
                .debounce(SEARCH_TIME_DELAY)
                .filter {
                    it?.isNotEmpty()!!
                }
                .onEach { text ->
                    text?.let {
                        val query = it.toString().trim()
                        searchViewModel.updateSearchQuery(query)
                    }
                }
                .launchIn(this)
        }
    }

    private fun setupTabsWithNavigation() {
        binding.tlSearch.apply {
            post {
                getTabAt(1)?.select()
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> {
                            navController.popBackStack()
                            navController.navigate(R.id.blog_search_list_fragment)
                        }

                        1 -> {
                            navController.popBackStack()
                            navController.navigate(R.id.video_search_list_fragment)
                        }

                        else -> {
                            navController.popBackStack()
                            navController.navigate(R.id.image_search_list_fragment)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}