package com.kenshi.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.kenshi.presentation.compose.ui.blog.BlogScreen
import com.kenshi.presentation.compose.ui.detail.SearchDetailScreen
import com.kenshi.presentation.compose.ui.image.ImageScreen
import com.kenshi.presentation.compose.ui.video.VideoScreen
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.item.video.VideoItem
import timber.log.Timber

@Composable
fun SearchNavHost(
    navController: NavHostController,
    searchQuery: String,
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
                onClickSeeBlogDetail = { urlType ->
                    navController.navigateToDetail(urlType)
                }
            )
        }
        composable(route = Video.route) {
            VideoScreen(
                videos = videos,
                onClickSeeVideoDetail = { urlType ->
                    navController.navigateToDetail(urlType)
                }
            )
        }
        composable(route = Image.route) {
            ImageScreen(
                images = images,
                onClickSeeImageDetail = { urlType ->
                    navController.navigateToDetail(urlType)
                }
            )
        }
        composable(
            route = SearchDetail.routeWithArgs,
            arguments = SearchDetail.arguments
        ) { navBackStackEntry ->
            val urlType = navBackStackEntry.arguments?.getString(SearchDetail.urlTypeArg)
            Timber.tag("SearchNavHost").d(urlType)
            SearchDetailScreen(urlType)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToDetail(urlType: String) {
    this.navigateSingleTopTo("${SearchDetail.route}/$urlType")
}