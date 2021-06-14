package com.example.youtubeapi.ui.viewVideo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.utils.StickyHeader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewVideo(
    videoId:String,
    videos:MutableState<List<VideoItem>>,
    vm:ViewVideoViewModel
){

    LaunchedEffect(videoId){
        vm.loadVideo(videoId)
    }

    vm.video.value.let { video ->

        if (video == null)
            ViewVideoPlaceholder()
        else
            Column{

                video.snippet.thumbnails?.Show {}

                LazyColumn {

                    item {
                        video.snippet.Show {}
                    }

                    item {
                        video.statistics?.Show(
                            inWatchLater = false,
                            onShare = { },
                            onWatchLater = {}
                        )
                    }

                    stickyHeader {
                        StickyHeader("Videos")
                    }
                    items(videos.value){item ->
                        VideoItemSmall(item.snippet) {
                            vm.setVideo(null)
                            vm.loadVideo(item.id!!)
                        }
                    }

                }
            }

    }

}
