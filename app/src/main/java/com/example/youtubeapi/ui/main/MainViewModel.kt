package com.example.youtubeapi.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository
): ViewModel() {

    private var nextPageToken:String?=""

    val message = mutableStateOf<String?>("")
    val videos= mutableStateOf<List<VideoItem>>(emptyList())
    val isLoading= mutableStateOf(true)

    init {
        viewModelScope.launch {
            repo.getVideos(nextPageToken){

                isLoading.value=false

                if (it.isSuccessful){
                    videos.value=it.data?.items ?: emptyList()
                    nextPageToken=it.data?.nextPageToken ?: ""
                }else
                    message.value=it.message

            }
        }
    }

}