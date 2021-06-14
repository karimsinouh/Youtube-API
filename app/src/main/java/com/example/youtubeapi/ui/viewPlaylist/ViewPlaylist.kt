package com.example.youtubeapi.ui.viewPlaylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
        if(vm.videos.value==null)
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
        if (vm.isLoadingVideos.value && vm.pageToken=="")
            CenterProgress()
        else{
            val videos=vm.videos.value?: emptyList()
            LazyColumn {

                if(!vm.isLoadingVideo.value)
                    item {
                        vm.video.value?.let {
                            Column {lo
                                it.snippet.Show {}
                                it.statistics?.Show(
                                    inWatchLater = true,
                                    onShare = {  },
                                    onWatchLater = {  }
                                )
                            }
                        }
                    }

                itemsIndexed(videos){ index,item->
                    val id=item.snippet.resourceId?.videoId!!
                    VideoItemSmall(snippet = item.snippet) {
                        vm.loadVideo(id)
                    }

                    if((index+1) == videos.size){
                        //the end of the list has been reached
                        if (vm.pageToken!="" && !vm.isLoadingVideos.value){
                            LaunchedEffect(playlist.id){
                                vm.loadVideos(playlist.id)
                            }
                        }
                    }

                }

                val isLoadingMore=vm.isLoadingVideos.value && vm.pageToken!=""
                if (isLoadingMore)
                    item{
                        CenterProgress(false)
                    }
            }
        }

    }
}