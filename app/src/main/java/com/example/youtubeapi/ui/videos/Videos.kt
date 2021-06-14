package com.example.youtubeapi.ui.videos

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.data.screen.ScreenState
import com.example.youtubeapi.ui.main.MainViewModel
import com.example.youtubeapi.utils.CenterProgress
import com.example.youtubeapi.utils.CoilImage
import com.example.youtubeapi.utils.defaultThumbnail

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Videos(
    vm:MainViewModel,
    nav:NavController
){
    vm.videosState.apply{

        if (isLoading.value && nextPageToken=="" && items.value.isEmpty() )
            CenterProgress()
        else
            LazyColumn {
                itemsIndexed(items.value){index,item->

                    val uid=item.snippet.resourceId?.videoId!!
                    VideoItem(videoId = uid, snippet =item.snippet ) {
                        nav.navigate("viewVideo/${item.snippet.resourceId.videoId}")
                    }

                    if ((index+1)==items.value.size){
                        //end of list reached
                        if (!isLoadingMore && canLoadMore)
                        LaunchedEffect(nextPageToken){
                            vm.loadVideos()
                        }
                    }

                }

                val isLoadingMore=isLoading.value && nextPageToken!=""
                if (isLoadingMore){
                    item {
                        CenterProgress(false)
                    }
                }
            }
    }
}

@Composable
fun VideoItem(
    videoId:String,
    snippet:Snippet,
    onClick:()->Unit
){
    Box(modifier = Modifier.clickable(onClick = onClick)){

        Column(
            Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            CoilImage(
                url = snippet.thumbnails?.high?.url?: defaultThumbnail,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Text(text = snippet.title,fontSize = 18.sp)
            Text(text = snippet.publishedAt,fontSize = 12.sp)
        }
    }
}

@Composable
fun VideoItemSmall(
    snippet:Snippet,
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
                url = snippet.thumbnails?.medium?.url?: defaultThumbnail,
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )


            Column {
                Text(text = snippet.title,fontSize = 18.sp,maxLines = 3)
                Text(text = "${snippet.publishedAt} videos",fontSize = 12.sp,maxLines = 1)
            }
        }
    }
}