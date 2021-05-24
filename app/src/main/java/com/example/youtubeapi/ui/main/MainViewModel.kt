package com.example.youtubeapi.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.data.screen.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository
): ViewModel() {

    val videosState= ScreenState.getInstance<VideoItem>()

    init {
        viewModelScope.launch {
            repo.getVideos(videosState.nextPageToken){

                videosState.setLoading(false)

                if (it.isSuccessful){
                    videosState.addItems(it.data?.items)
                    videosState.nextPageToken=it.data?.nextPageToken
                }else
                    videosState.setMessage(it.message)

            }
        }
    }

}