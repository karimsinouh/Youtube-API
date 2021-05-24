package com.example.youtubeapi.ui.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youtubeapi.R
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.data.screen.ScreenState
import com.example.youtubeapi.ui.videos.CenterProgress
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun Playlists(playlistsState:ScreenState<PlaylistItem>){
    if (playlistsState.isLoading.value)
        CenterProgress()
    else
        LazyColumn {
            items(playlistsState.items.value){item->
                PlaylistItem(item){}
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
            Image(
                painter = rememberCoilPainter(item.snippet.thumbnails.medium.url),
                "",
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(text = item.snippet.title,fontSize = 18.sp,maxLines = 3)
                Text(text = "${item.contentDetails.itemCount} videos",fontSize = 12.sp,maxLines = 1)
            }
        }
    }
}