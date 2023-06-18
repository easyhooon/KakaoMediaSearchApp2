package com.kenshi.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kenshi.presentation.compose.ui.Blog
import com.kenshi.presentation.compose.ui.Detail
import com.kenshi.presentation.compose.ui.Image
import com.kenshi.presentation.compose.ui.Video
import com.kenshi.presentation.compose.ui.blog.BlogScreen
import com.kenshi.presentation.compose.ui.image.ImageScreen
import com.kenshi.presentation.compose.ui.video.VideoScreen

@Composable
fun SearchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Video.route,
        modifier = modifier,
    ) {
        composable(route = Blog.route) {
            BlogScreen(
                onClickSeeBlogDetail = { accountType ->
                    navController.navigateSingleTopTo(accountType)
                }
            )
        }
        composable(route = Video.route) {
            VideoScreen(
                onClickSeeVideoDetail = { accountType ->
                    navController.navigateSingleTopTo(accountType)
                }
            )
        }
        composable(route = Image.route) {
            ImageScreen(
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