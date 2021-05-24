package com.example.youtubeapi.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.youtubeapi.ui.downloads.Downloads
import com.example.youtubeapi.ui.favorites.Favorites
import com.example.youtubeapi.ui.playlists.Playlists
import com.example.youtubeapi.ui.videos.Videos
import com.example.youtubeapi.R

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
fun MainRow(
    selectedPosition:Int,
    onTabSelected:(Screen)->Unit
){

    val screens=Screen.All.list

    ScrollableTabRow(
        edgePadding=0.dp,
        selectedTabIndex =selectedPosition,
        contentColor=MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        tabs={
            screens.forEach {

                Tab(
                    selectedPosition == it.position,
                    onClick = { onTabSelected(it) }
                ) {
                    Text(stringResource(it.title),Modifier.padding(14.dp))
                }

            }
        },
    )
}

@Composable
fun MainSheet(){

}

@Composable
fun MainNavHost(
    controller:NavHostController,
    vm:MainViewModel
){
    NavHost(
        navController = controller,
        startDestination = Screen.Videos.route
    ){


        composable(Screen.Videos.route){
            Videos(vm.videosState)
        }

        composable(Screen.Playlists.route){
            Playlists(vm.playlistsState)
        }

        composable(Screen.Favorites.route){
            Favorites()
        }

        composable(Screen.Downloads.route){
            Downloads()
        }

    }
}