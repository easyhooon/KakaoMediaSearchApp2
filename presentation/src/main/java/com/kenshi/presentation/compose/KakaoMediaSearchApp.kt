package com.kenshi.presentation.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kenshi.presentation.compose.ui.Video
import com.kenshi.presentation.compose.ui.components.SearchTabRow
import com.kenshi.presentation.compose.ui.searchTabRowScreens
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme

@Composable
fun KakaoMediaSearchApp() {
    KakaoMediaSearchApp2Theme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            searchTabRowScreens.find { it.route == currentDestination?.route } ?: Video

        Scaffold(
            topBar = {
                SearchTabRow(
                    allScreens = searchTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            SearchNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}