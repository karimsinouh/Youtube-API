package com.example.youtubeapi.ui.playlists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.data.screen.ScreenState
import com.example.youtubeapi.ui.main.MainViewModel
import com.example.youtubeapi.utils.CenterProgress
import com.example.youtubeapi.utils.CoilImage
import com.example.youtubeapi.utils.defaultThumbnail

@Composable
fun Playlists(
    vm:MainViewModel,
    nav:NavController
){
    vm.playlistsState.apply {

        if (isLoading.value && nextPageToken=="")
            CenterProgress()
        else
            LazyColumn {
                itemsIndexed(items.value){index,item->
                    PlaylistItem(item){
                        nav.navigate("viewPlaylist/${item.id}")
                    }

                    if((index+1)==items.value.size){
                        //the end of the list has been reached
                        if (canLoadMore && !isLoadingMore)
                            vm.loadPlaylists()
                    }
                }

                val isLoadingMore=isLoading.value && nextPageToken!=""
                if (isLoadingMore)
                    item{
                        CenterProgress(false)
                    }

            }

    }
}

@Composable
fun PlaylistItem(
    item:PlaylistItem,
    onClick:()->Unit
){
    Box(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CoilImage(
                url = item.snippet.thumbnails?.medium?.url?: defaultThumbnail,
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )


            Column {
                Text(text = item.snippet.title,fontSize = 18.sp,maxLines = 3)
                Text(text = "${item.contentDetails.itemCount} videos",fontSize = 12.sp,maxLines = 1)
            }
        }
    }
}