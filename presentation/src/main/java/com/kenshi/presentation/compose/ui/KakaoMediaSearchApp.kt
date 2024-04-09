package com.kenshi.presentation.compose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                value = searchQuery,
//                singleLine = true,
//                onValueChange = { query ->
//                    viewModel.updateSearchQuery(query)
//                },
//                leadingIcon = {
//                    Icon(
//                        Icons.Filled.Search,
//                        contentDescription = "Search Icon"
//                    )
//                },
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                placeholder = {
//                    Text(
//                        text = stringResource(R.string.search),
//                        fontWeight = FontWeight.Thin
//                    )
//                },
//            )
            BasicTextField(
                value = searchQuery,
                onValueChange = { query ->
                    viewModel.updateSearchQuery(query)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 8.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(
                                border = BorderStroke(width = 1.dp, color = Color(0xFF111827)),
                                shape = RoundedCornerShape(8.dp),
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(modifier = Modifier.width(18.dp))
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color(0xFF111827),
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Box(
                        ) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search),
                                    color = Color(0xFF9ca3af),
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                            innerTextField()
                        }
                    }
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