package com.example.youtubeapi.ui.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.youtubeapi.ui.downloads.Downloads
import com.example.youtubeapi.ui.favorites.Favorites
import com.example.youtubeapi.ui.playlists.Playlists
import com.example.youtubeapi.ui.videos.Videos
import com.example.youtubeapi.ui.viewPlaylist.ViewPlaylist
import com.example.youtubeapi.ui.viewPlaylist.ViewPlaylistViewModel
import com.example.youtubeapi.ui.viewVideo.ViewVideo
import com.example.youtubeapi.ui.viewVideo.ViewVideoViewModel
import com.example.youtubeapi.utils.findPlaylistById

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

    val screens=Screen.Items.rowItems

    ScrollableTabRow(
        edgePadding=0.dp,
        selectedTabIndex =selectedPosition,
        contentColor=MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        tabs={
            screens.forEach {

                val isSelected=selectedPosition == it.position

                Tab(
                    isSelected,
                    onClick = {
                        if (!isSelected) onTabSelected(it)
                    }
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
            Videos(vm.videosState,controller)
        }

        composable(Screen.Playlists.route){
            Playlists(vm.playlistsState,controller)
        }

        composable(Screen.WatchLater.route){
            Favorites()
        }

        composable(Screen.Downloads.route){
            Downloads()
        }

        composable("viewVideo/{videoId}"){
            val viewVideoViewModel= hiltViewModel<ViewVideoViewModel>()
            ViewVideo(videoId = it.arguments?.getString("videoId")!!, videos =vm.videosState.items,viewVideoViewModel)
        }

        composable("viewPlaylist/{playlistId}"){
            val viewPlaylistViewModel= hiltViewModel<ViewPlaylistViewModel>()
            val playlistId=it.arguments?.getString("playlistId")!!
            val playlist=vm.playlistsState.items.value.findPlaylistById(playlistId)
            ViewPlaylist(playlist = playlist!!, vm = viewPlaylistViewModel)
        }

    }
}