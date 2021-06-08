package com.example.youtubeapi.ui.videos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.data.screen.ScreenState
import com.example.youtubeapi.ui.main.MainViewModel
import com.example.youtubeapi.utils.CenterProgress
import com.example.youtubeapi.utils.CoilImage
import com.example.youtubeapi.utils.Placeholder
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Videos(
    videosState:ScreenState<VideoItem>,
    nav:NavController
){
    videosState.apply{

        if (isLoading.value)
            CenterProgress()
        else
            LazyColumn {
                items(items.value){item->
                    VideoItem(videoId = item.snippet.resourceId?.videoId!!, snippet =item.snippet ) {
                        nav.navigate("viewVideo/${item.snippet.resourceId.videoId}")
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
                url = snippet.thumbnails.high.url,
                modifier = Modifier.fillMaxWidth().height(190.dp).clip(RoundedCornerShape(8.dp))
            )

            Text(text = snippet.title,fontSize = 18.sp)
            Text(text = snippet.publishedAt,fontSize = 12.sp)
        }
    }
}