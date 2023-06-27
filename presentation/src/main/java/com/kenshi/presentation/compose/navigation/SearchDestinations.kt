package com.kenshi.presentation.compose.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface SearchDestination {
    val route: String
    val title: String
}

object Blog: SearchDestination {
    override val route = "blog"
    override val title = "블로그"
}

object Video: SearchDestination {
    override val route = "video"
    override val title = "동영상"
}

object Image: SearchDestination {
    override val route = "image"
    override val title = "사진"
}

object SearchDetail: SearchDestination {
    override val route = "detail"
    override val title = "상세 화면"
    const val urlTypeArg = "url_type"
    val routeWithArgs = "$route/{$urlTypeArg}"
    val arguments = listOf(
        navArgument(urlTypeArg) { type = NavType.StringType}
    )
}