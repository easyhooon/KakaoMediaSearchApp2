package com.kenshi.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.kenshi.presentation.compose.ui.screens.BlogScreen
import com.kenshi.presentation.compose.ui.screens.detail.SearchDetailScreen
import com.kenshi.presentation.compose.ui.screens.ImageScreen
import com.kenshi.presentation.compose.ui.screens.VideoScreen
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.item.video.VideoItem
import navigateSingleTopTo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchNavHost(
    navController: NavHostController,
    searchQuery: String,
    debouncedSearchQuery: String,
    blogs: LazyPagingItems<BlogItem>,
    videos: LazyPagingItems<VideoItem>,
    images: LazyPagingItems<ImageItem>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Video.route,
        modifier = modifier,
    ) {
        composable(route = Blog.route) {
            BlogScreen(
                blogs = blogs,
                searchQuery = searchQuery,
                debouncedSearchQuery = debouncedSearchQuery,
                onClickBlogDetail = { urlType ->
                    val encodedUrl = URLEncoder.encode(urlType, StandardCharsets.UTF_8.toString())
                    navController.navigateToDetail(encodedUrl)
                }
            )
        }
        composable(route = Video.route) {
            VideoScreen(
                videos = videos,
                searchQuery = searchQuery,
                debouncedSearchQuery = debouncedSearchQuery,
                onClickVideoDetail = { urlType ->
                    val encodedUrl = URLEncoder.encode(urlType, StandardCharsets.UTF_8.toString())
                    navController.navigateToDetail(encodedUrl)
                }
            )
        }
        composable(route = Image.route) {
            ImageScreen(
                images = images,
                searchQuery = searchQuery,
                debouncedSearchQuery = debouncedSearchQuery,
                onClickImageDetail = { urlType ->
                    val encodedUrl = URLEncoder.encode(urlType, StandardCharsets.UTF_8.toString())
                    navController.navigateToDetail(encodedUrl)
                }
            )
        }
        composable(
            route = SearchDetail.routeWithArgs,
            arguments = SearchDetail.arguments
        ) {
            SearchDetailScreen()
        }
    }
}

private fun NavHostController.navigateToDetail(urlType: String) {
    this.navigateSingleTopTo("${SearchDetail.route}/$urlType")
    // don't do this
    // this.navigateSingleTopTo("${SearchDetail.route}?${SearchDetail.urlTypeArg}=$urlType")
}