package com.kenshi.presentation.compose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kenshi.presentation.compose.navigation.SearchDestination

@Composable
fun SearchTabRow(
    allScreens: List<SearchDestination>,
    onTabSelected: (SearchDestination) -> Unit,
    currentScreen: SearchDestination
) {
    val selectedIndex = allScreens.indexOf(currentScreen)

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        allScreens.forEach { screen ->
            SearchTab(
                text = screen.title,
                onSelected = { onTabSelected(screen) },
                selected = currentScreen == screen,
            )
        }
    }
}

@Composable
private fun SearchTab(
    text: String,
    onSelected: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text
        )
    }
}

private val TabHeight = 56.dp