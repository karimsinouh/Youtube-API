package com.example.youtubeapi.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MainToolbar(){
    TopAppBar(
        title = { Text("Youtube API") },
        actions = {

            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Search, "")
            }

            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Settings, "")
            }
        },
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp
    )
}

@Composable
@Preview
fun MainRow(){

    ScrollableTabRow(
        edgePadding=0.dp,
        selectedTabIndex =1,
        contentColor=MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        tabs={

            Tab(selected = false, onClick = { /*TODO*/ }) {
                Text(text = "Videos", Modifier.padding(8.dp))
            }

            Tab(selected = true, onClick = { /*TODO*/ }) {
                Text(text = "Videos", Modifier.padding(8.dp))
            }

            Tab(selected = false, onClick = { /*TODO*/ }) {
                Text(text = "Videos", Modifier.padding(8.dp))
            }

        }
    )
}
