package com.kenshi.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kenshi.presentation.R
import com.kenshi.presentation.compose.navigation.SearchNavHost
import com.kenshi.presentation.compose.navigation.Video
import com.kenshi.presentation.compose.navigation.navigateSingleTopTo
import com.kenshi.presentation.compose.navigation.searchTabRowScreens
import com.kenshi.presentation.compose.ui.components.SearchTabRow
import com.kenshi.presentation.compose.ui.theme.KakaoMediaSearchApp2Theme

@Composable
fun KakaoMediaSearchApp() {
    KakaoMediaSearchApp2Theme {
        val navController = rememberNavController()
        var searchQuery by remember { mutableStateOf("") }
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            searchTabRowScreens.find { it.route == currentDestination?.route } ?: Video
        val context = LocalContext.current

        Surface(color = Color.White) {
            Column {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(context.getString(R.string.search)) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") }
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}