package com.example.youtubeapi.ui.viewVideo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.uiplayVideo.PlayVideoActivity
import com.example.youtubeapi.utils.StickyHeader
import com.example.youtubeapi.utils.shareVideo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewVideo(
    videoId:String,
    videos:MutableState<List<VideoItem>>,
    vm:ViewVideoViewModel=hiltViewModel(),
    onShowBottomSheet:(Snippet)->Unit
){

    val context= LocalContext.current

    LaunchedEffect(videoId){
        vm.loadVideo(videoId)
    }

    Surface(color=MaterialTheme.colors.background) {
        vm.video.value.let { video ->

            if (video == null)
                ViewVideoPlaceholder()
            else
                Column{

                    val inWatchLater=vm.exists(video.id!!).observeAsState(false)


                    video.snippet.thumbnails?.Show {
                        PlayVideoActivity.open(context,video.id)
                    }

                    LazyColumn {

                        item {
                            video.snippet.Show {
                                onShowBottomSheet(video.snippet)
                            }
                        }

                        item {
                            video.statistics?.Show(
                                inWatchLater = inWatchLater.value,
                                onShare = {
                                          shareVideo(context,video.id)
                                },
                                onWatchLater = {
                                    vm.onWatchLater(video.id,it)
                                }
                            )
                        }

                        stickyHeader {
                            StickyHeader("Videos")
                        }

                        items(videos.value){item ->
                            VideoItemSmall(item.snippet) {
                                vm.setVideo(null)
                                vm.loadVideo(item.snippet.resourceId?.videoId!!)
                            }
                        }

                    }
                }

        }
    }

}
