package com.example.youtubeapi.ui.viewPlaylist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.ui.viewVideo.Show
import com.example.youtubeapi.uiplayVideo.PlayVideoActivity
import com.example.youtubeapi.utils.CenterProgress
import com.example.youtubeapi.utils.StickyHeader
import com.example.youtubeapi.utils.shareVideo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPlaylist(
    playlist:PlaylistItem,
    vm:ViewPlaylistViewModel = hiltViewModel(),
){

    val context= LocalContext.current

    LaunchedEffect(playlist.id){
        vm.loadVideos(playlist.id)
    }

    Surface(color= MaterialTheme.colors.background) {
        Column {

            //header
            vm.video.value.let { video->

                if (video==null){
                    playlist.snippet.thumbnails?.Show(false){}
                    playlist.snippet.Show {}
                }else if (!vm.isLoadingVideo.value){
                    video.snippet.thumbnails?.Show {
                        PlayVideoActivity.open(context,video.id!!)
                    }
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
                            vm.video.value?.let { video ->

                                val inWatchLater=vm.exists(video.id!!).observeAsState(false)

                                Column {
                                    video.snippet.Show {}
                                    video.statistics?.Show(
                                        inWatchLater = inWatchLater.value,
                                        onShare = {
                                            shareVideo(context,video.id)
                                        },
                                        onWatchLater = { vm.onWatchLater(video.id,it) }
                                    )
                                }
                            }
                        }

                    stickyHeader {
                        StickyHeader("Videos")
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
}