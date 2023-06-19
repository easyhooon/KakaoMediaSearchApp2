package com.kenshi.presentation.compose.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface SearchDestination {
    val route: String
}

object Blog: SearchDestination {
    override val route = "블로그"
}

object Video: SearchDestination {
    override val route = "동영상"
}

object Image: SearchDestination {
    override val route = "사진"
}

object Detail: SearchDestination {
    override val route = "detail"
    const val urlTypeArg = "url_type"
    val routeWithArgs = "${route}/{$urlTypeArg}"
    val arguments = listOf(
        navArgument(urlTypeArg) { type = NavType.StringType}
    )
}

val searchTabRowScreens = listOf(Blog, Video, Image)