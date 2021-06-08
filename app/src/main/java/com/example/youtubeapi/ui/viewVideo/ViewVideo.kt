package com.example.youtubeapi.ui.viewVideo

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.youtubeapi.data.items.VideoItem

@Composable
fun ViewVideo(
    videoId:String,
    videos:MutableState<List<VideoItem>>,
    vm:ViewVideoViewModel
){
    vm.loadVideo(videoId)


    Column{

        vm.video.value.let { video ->

            if (video == null)
                ViewVideoPlaceholder()
            else {

                video.snippet.thumbnails.Show {}
                video.snippet.Show {}
                video.statistics?.Show(
                    inWatchLater = false,
                    onShare = { },
                    onWatchLater = {}
                )
            }

        }
    }


}