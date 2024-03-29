package com.kenshi.presentation.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.kenshi.presentation.R
import com.kenshi.presentation.SearchViewModel
import com.kenshi.presentation.compose.navigation.Blog
import com.kenshi.presentation.compose.navigation.Image
import com.kenshi.presentation.compose.navigation.SearchNavHost
import com.kenshi.presentation.compose.navigation.Video
import com.kenshi.presentation.compose.ui.components.SearchTabRow
import navigateSingleTopTo

//TODO View 의 theme 적용
@Composable
fun KakaoMediaSearchApp(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val debouncedSearchQuery by viewModel.debouncedSearchQuery.collectAsState(null)
    val blogItems = viewModel.searchBlogs.collectAsLazyPagingItems()
    val videoItems = viewModel.searchVideos.collectAsLazyPagingItems()
    val imageItems = viewModel.searchImages.collectAsLazyPagingItems()
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = searchTabRowScreens.find { it.route == currentDestination?.route } ?: Video

    Surface(color = Color.White) {
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = searchQuery,
                singleLine = true,
                onValueChange = { query ->
                    viewModel.updateSearchQuery(query)
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        fontWeight = FontWeight.Thin
                    )
                },
            )
            SearchTabRow(
                allScreens = searchTabRowScreens,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen
            )
            SearchNavHost(
                navController = navController,
                searchQuery = searchQuery,
                debouncedSearchQuery = debouncedSearchQuery ?: "",
                blogs = blogItems,
                videos = videoItems,
                images = imageItems,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            )
        }
    }
}

private val searchTabRowScreens = listOf(Blog, Video, Image)