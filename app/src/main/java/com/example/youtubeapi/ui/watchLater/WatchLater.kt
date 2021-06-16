package com.example.youtubeapi.ui.watchLater

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youtubeapi.database.WatchLaterVideo
import com.example.youtubeapi.ui.main.MainViewModel
import com.example.youtubeapi.ui.main.Screen
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.utils.CoilImage
import com.example.youtubeapi.utils.defaultThumbnail

@Composable
fun WatchLater(
    vm:MainViewModel,
    nav:NavController
){

    val watchLater=vm.watchLater.observeAsState(emptyList())

    LazyColumn {
        items(watchLater.value){item->
            WatchLaterItem(item){
                nav.navigate(Screen.ViewVideo.route+"/"+item.videoId)
            }
        }
    }
}

@Composable
fun WatchLaterItem(
    item: WatchLaterVideo,
    onClick:()->Unit,
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
                url = item.thumbnail,
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )


            Column {
                Text(text = item.title,fontSize = 18.sp,maxLines = 2)
            }
        }
    }
}