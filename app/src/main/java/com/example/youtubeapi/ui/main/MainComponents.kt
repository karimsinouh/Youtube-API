package com.example.youtubeapi.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.youtubeapi.R
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.ui.watchLater.WatchLater
import com.example.youtubeapi.ui.playlists.Playlists
import com.example.youtubeapi.ui.search.Search
import com.example.youtubeapi.ui.theme.Shapes
import com.example.youtubeapi.ui.videos.Videos
import com.example.youtubeapi.ui.viewPlaylist.ViewPlaylist
import com.example.youtubeapi.ui.viewVideo.ViewVideo
import com.example.youtubeapi.utils.ExpandableStickyHeader
import com.example.youtubeapi.utils.findPlaylistById

@Composable
fun MainToolbar(
    onNavigationClick:()->Unit,
    onSearchClick:()->Unit
){
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        actions = {

            IconButton(onSearchClick) {
                Icon(Icons.Outlined.Search, null)
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

        selectedTabIndex =selectedPosition,
        contentColor=MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        tabs={
            screens.forEach {screen->

                val isSelected=selectedPosition == screen.position

                Tab(
                    isSelected,
                    onClick = {
                        if (!isSelected) onTabSelected(screen)
                    }
                ) {
                    Row(
                        Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Icon(painterResource(screen.icon!!),null)
                        Text(stringResource(screen.title))
                    }
                }

            }
        },
        edgePadding = 8.dp
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

        composable(Screen.Search.route){
            Search(nav = controller)
        }

        composable(Screen.Videos.route){
            Videos(vm,controller)
        }

        composable(Screen.Playlists.route){
            Playlists(vm,controller)
        }

        composable(Screen.WatchLater.route){
            WatchLater(vm,controller)
        }

        composable("${Screen.ViewVideo.route}/{videoId}"){
            ViewVideo(videoId = it.arguments?.getString("videoId")!!, videos =vm.videosState.items)
        }

        composable("${Screen.ViewPlaylist.route}/{playlistId}"){
            val playlistId=it.arguments?.getString("playlistId")!!
            val playlist=vm.playlistsState.items.value.findPlaylistById(playlistId)
            ViewPlaylist(playlist = playlist!!)
        }

    }
}



@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainDrawer(
    darkMode:Boolean,
    selectedScreenRoute: String,
    playlists:List<PlaylistItem>,
    onDarkModeChanges:(Boolean)->Unit,
    onNavigate:(Screen)->Unit,
    onPlaylistClick:(String)->Unit
){
    val showPlaylists= remember {
        mutableStateOf(true)
    }

    Column(Modifier.padding(12.dp)){

        //top settings icon
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = {
                onDarkModeChanges(!darkMode)
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
        Screen.Items.drawerItems.forEach {

            val isSelected=selectedScreenRoute==it.route

            val backgroundColor=if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else MaterialTheme.colors.background
            val contentColor=if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground


            ListItem(
                icon={Icon( painter = painterResource(it.icon!!), null ,tint = contentColor)},
                text={ Text(stringResource(it.title),color=contentColor) },
                modifier = Modifier
                    .clickable { onNavigate(it) }
                    .clip(Shapes.small)
                    .background(backgroundColor),
            )

            Spacer(Modifier.height(4.dp))
        }

        if(playlists.isNotEmpty()){

            ExpandableStickyHeader("Playlists",showPlaylists.value){
                showPlaylists.value= !showPlaylists.value
            }

            AnimatedVisibility(showPlaylists.value) {
                Column(Modifier.verticalScroll(rememberScrollState())){

                    playlists.forEach {
                        ListItem(
                            text = { Text(text = it.snippet.title,maxLines = 1) },
                            overlineText = { Text("${it.contentDetails?.itemCount} videos") },
                            modifier=Modifier.clickable { onPlaylistClick(it.id) }
                        )
                    }

                }
            }

        }
    }
}