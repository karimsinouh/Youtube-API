package com.example.youtubeapi.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.youtubeapi.data.items.SearchItem
import com.example.youtubeapi.ui.playlists.PlaylistItem
import com.example.youtubeapi.ui.videos.VideoItem
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.utils.CenterProgress

@Composable
fun Search(vm:SearchViewModel= hiltViewModel(),nav:NavController){

        Surface(color=MaterialTheme.colors.background) {

            Column(Modifier.padding(12.dp)) {
                SearchBar(
                    vm.searchQuery.value,
                    onValueChange = {vm.searchQuery.value=it}
                ) {
                    vm.pageToken=""
                    vm.items.clear()
                    vm.search()
                }

                Spacer(Modifier.height(8.dp))

                val isLoading=vm.isLoading.value

                if (isLoading && vm.pageToken==""){
                    CenterProgress()
                }else{
                    val items=vm.items
                    LazyColumn {


                        itemsIndexed(items){index,item->
                            SearchItem(
                                item=item,
                                onPlaylistClick = {
                                    nav.navigate("viewPlaylist/$it")
                                },
                                onVideoClick = {
                                    nav.navigate("viewVideo/$it")
                                }
                            )

                            if ((index+1)==items.size){
                                //the end of the list has been reached
                                if(vm.pageToken!="" && !isLoading){
                                    SideEffect {
                                        vm.search()
                                    }
                                }
                            }

                        }

                        if(isLoading && vm.pageToken!=""){
                            item {
                                CenterProgress(false)
                            }
                        }

                    }

                }

            }

        }

}

@Composable
private fun SearchBar(
    value:String,
    onValueChange:(q:String)->Unit,
    onSearchClick:()->Unit
){


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
    ) {

        BasicTextField(
            modifier= Modifier
                .weight(0.9f)
                .padding(12.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            cursorBrush= SolidColor(MaterialTheme.colors.primary)
        )

        IconButton(onClick = { onSearchClick() }) {
            Icon(Icons.Outlined.Search,"")
        }
    }
}

@Composable
private fun SearchItem(
    item:SearchItem,
    onPlaylistClick:(String)->Unit,
    onVideoClick:(String)->Unit
){
    if(item.id.kind=="youtube#video"){
        VideoItem(snippet = item.snippet) {
            onVideoClick(item.id.videoId!!)
        }
    }else if(item.id.kind=="youtube#playlist"){
        PlaylistItem(item = item.asPlaylistItem()) {
            onPlaylistClick(item.id.playlistId!!)
        }
    }
}