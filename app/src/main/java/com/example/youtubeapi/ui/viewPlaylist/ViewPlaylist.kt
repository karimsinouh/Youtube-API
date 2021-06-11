package com.example.youtubeapi.ui.viewPlaylist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.ui.videos.VideoItem
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.ui.viewVideo.Show
import com.example.youtubeapi.ui.viewVideo.ViewVideoPlaceholder
import com.example.youtubeapi.utils.CenterProgress

@Composable
fun ViewPlaylist(
    playlist:PlaylistItem,
    vm:ViewPlaylistViewModel,
){


    LaunchedEffect(playlist.id){
        vm.loadVideos(playlist.id)
    }

    Column {

        //header
        vm.video.value.let { video->

            if (video==null){
                playlist.snippet.thumbnails.Show(false){}
                playlist.snippet.Show {}
            }else{

                if(vm.isLoadingVideo.value)
                    ViewVideoPlaceholder()
                else{
                    video.snippet.thumbnails.Show {}
                    video.snippet.Show {}
                    video.statistics?.Show(inWatchLater = false, onShare = {  }) {}
                }
            }

        }

        //videos list
        vm.videos.value.let { videos->
            if (videos==null){
                CenterProgress()
            }else{
                LazyColumn {

                    items(videos){item->
                        val id=item.snippet.resourceId?.videoId!!
                        VideoItemSmall(snippet = item.snippet) {
                            vm.loadVideo(id)
                        }
                    }

                }
            }
        }

    }
}