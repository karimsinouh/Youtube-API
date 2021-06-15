package com.example.youtubeapi.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.youtubeapi.R
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
fun MainToolbar(
    onNavigationClick:()->Unit,
    onSearchClick:()->Unit
){
    TopAppBar(
        title = { Text("Youtube API") },
        actions = {

            IconButton(onSearchClick) {
                Icon(Icons.Outlined.Search, null)
            }

            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Settings, "")
            }
        },
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(Icons.Outlined.Menu,null)
            }
        }
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
            Videos(vm,controller)
        }

        composable(Screen.Playlists.route){
            Playlists(vm,controller)
        }

        composable(Screen.WatchLater.route){
            Favorites()
        }

        composable(Screen.Downloads.route){
            Downloads()
        }

        composable("${Screen.ViewVideo.route}/{videoId}"){
            //val viewVideoViewModel= hiltViewModel<ViewVideoViewModel>()
            ViewVideo(videoId = it.arguments?.getString("videoId")!!, videos =vm.videosState.items)
        }

        composable("${Screen.ViewPlaylist.route}/{playlistId}"){
            //val viewPlaylistViewModel= hiltViewModel<ViewPlaylistViewModel>()
            val playlistId=it.arguments?.getString("playlistId")!!
            val playlist=vm.playlistsState.items.value.findPlaylistById(playlistId)
            ViewPlaylist(playlist = playlist!!)
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainDrawer(
    darkMode:Boolean,
    selectedScreenRoute: String,
    onDarkModeChanges:()->Unit,
    onNavigate:(Screen)->Unit,
){
    Column(Modifier.padding(12.dp)){

        //top settings icon
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = {
                onDarkModeChanges()
            }) {
                if (darkMode){
                    Icon(painter = painterResource(R.drawable.ic_light_mode), null)
                }else{
                    Icon(painter = painterResource(R.drawable.ic_dark_mode), null)
                }
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        //navigation
        Screen.Items.rowItems.forEach {
            ListItem(
                icon={Icon( painter = painterResource(it.icon!!), null )},
                text={ Text(stringResource(it.title)) },
                trailing = { Icon(Icons.Outlined.KeyboardArrowRight, null )},
                modifier = Modifier.clickable { onNavigate(it) },
            )
        }
    }
}