package com.kenshi.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.kenshi.presentation.compose.ui.blog.BlogScreen
import com.kenshi.presentation.compose.ui.image.ImageScreen
import com.kenshi.presentation.compose.ui.video.VideoScreen
import com.kenshi.presentation.item.blog.BlogItem
import com.kenshi.presentation.item.image.ImageItem
import com.kenshi.presentation.item.video.VideoItem

@Composable
fun SearchNavHost(
    navController: NavHostController,
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
                blogs,
                onClickSeeBlogDetail = { accountType ->
                    navController.navigateSingleTopTo(accountType)
                }
            )
        }
        composable(route = Video.route) {
            VideoScreen(
                videos,
                onClickSeeVideoDetail = { accountType ->
                    navController.navigateSingleTopTo(accountType)
                }
            )
        }
        composable(route = Image.route) {
            ImageScreen(
                images,
                onClickSeeImageDetail = { accountType ->
                    navController.navigateSingleTopTo(accountType)
                }
            )
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

private fun NavHostController.navigateToDetail(query: String) {
    this.navigateSingleTopTo("${Detail.route}/$query")
}