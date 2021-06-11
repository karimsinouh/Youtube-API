package com.example.youtubeapi.ui.viewPlaylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.utils.CenterProgress
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.ui.viewVideo.Show

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
                playlist.snippet.thumbnails?.Show(false){}
                playlist.snippet.Show {}
            }else if (!vm.isLoadingVideo.value){
                video.snippet.thumbnails?.Show {}
            }

        }

        //videos list
        vm.videos.value.let { videos->
            if (videos==null){
                CenterProgress()
            }else{
                LazyColumn {

                    if(!vm.isLoadingVideo.value)
                        item {
                            vm.video.value?.let {
                                Column {
                                    it.snippet.Show {}
                                    it.statistics?.Show(
                                        inWatchLater = true,
                                        onShare = {  },
                                        onWatchLater = {  }
                                    )
                                }
                            }
                        }

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