package com.kenshi.kakaomediasearchapp2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun KakaoMediaSearchApp() {
    val navHostController = rememberNavController()
    val backStackEntry by navHostController.currentBackStackEntryAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button (
                modifier = Modifier.size(120.dp),
                onClick = {}
            ) {
                Text(
                    text = "View",
                )
            }

            Button(
                modifier = Modifier.size(120.dp),
                onClick = {}
            ) {
                Text(
                    text = "Compose"
                )
            }
        }
    }
}

@Preview
@Composable
fun KakaoMediaSearchAppPreview() {
    KakaoMediaSearchApp()
}