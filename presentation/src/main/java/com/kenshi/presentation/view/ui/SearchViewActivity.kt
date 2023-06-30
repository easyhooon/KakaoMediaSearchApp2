package com.kenshi.presentation.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.kenshi.presentation.R
import com.kenshi.presentation.databinding.ActivitySearchViewBinding
import com.kenshi.presentation.util.Constants.SEARCH_TIME_DELAY
import com.kenshi.presentation.util.hideKeyboard
import com.kenshi.presentation.util.repeatOnStarted
import com.kenshi.presentation.util.textChangesToFlow
import com.kenshi.presentation.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@AndroidEntryPoint
class SearchViewActivity : BaseActivity<ActivitySearchViewBinding>(R.layout.activity_search_view) {

    private val viewModel by viewModels<SearchViewModel>()

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun getViewBinding() = ActivitySearchViewBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTabsWithNavigation()
        initObserver()
    }

    private fun initObserver() {
        repeatOnStarted {
            launch {
                val editTextFlow = binding.etSearch.textChangesToFlow()
                editTextFlow
                    .debounce(SEARCH_TIME_DELAY)
                    .filter {
                        it?.isNotEmpty()!!
                    }
                    .collect { text ->
                        text?.let {
                            val query = it.toString().trim()
                            viewModel.updateSearchQuery(query)
                        }
                    }
            }
            launch {
                viewModel.searchQuery.collect {
                    hideKeyboard()
                }
            }
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
                            // navController.navigateSingleTopTo(R.id.blog_search_list_fragment)
                        }

                        1 -> {
                            navController.popBackStack()
                            navController.navigate(R.id.video_search_list_fragment)
                            // navController.navigateSingleTopTo(R.id.video_search_list_fragment)
                        }

                        else -> {
                            navController.popBackStack()
                            navController.navigate(R.id.image_search_list_fragment)
                            // navController.navigateSingleTopTo(R.id.image_search_list_fragment)
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

    fun NavController.navigateSingleTopTo(
        @IdRes destinationId: Int,
        args: Bundle? = null,
    ) {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(graph.startDestinationId, false)
        navigate(destinationId, args, builder.build())
    }
}