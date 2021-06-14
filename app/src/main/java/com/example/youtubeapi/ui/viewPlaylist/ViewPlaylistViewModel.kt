package com.example.youtubeapi.ui.viewPlaylist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.utils.addAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPlaylistViewModel @Inject constructor(
    private val youtube:Repository
) :ViewModel() {

    var pageToken=""

    val videos= mutableStateOf<List<VideoItem>?>(null)
    val video= mutableStateOf<VideoItem?>(null)
    val message= mutableStateOf<String?>(null)
    val isLoadingVideo= mutableStateOf(false)
    val isLoadingVideos= mutableStateOf(false)

    fun loadVideo(id:String)=viewModelScope.launch {
        youtube.getVideo(id){
            if(it.isSuccessful)
                video.value=it.data
            else
                message.value=it.message
        }
    }

    fun loadVideos(playlistId:String)=viewModelScope.launch{
        isLoadingVideos.value=true
        delay(1000)
        youtube.getPlaylistVideos(playlistId,pageToken){
            isLoadingVideos.value=false
            if(it.isSuccessful){
                videos.addAll(it.data?.items)
                pageToken=it.data?.nextPageToken?:""
            }else
                message.value=it.message

        }
    }

}