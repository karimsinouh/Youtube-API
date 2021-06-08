package com.example.youtubeapi.ui.viewVideo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewVideoViewModel @Inject constructor(
    private val repo:Repository
):ViewModel() {

    val video= mutableStateOf<VideoItem?>(null)
    val message= mutableStateOf<String?>(null)

    fun loadVideo(id:String)=viewModelScope.launch{
        repo.getVideo(id){
            if (it.isSuccessful)
                video.value=it.data
            else
                setMessage(it.message)
        }
    }

    fun setMessage(value:String?){
        message.value=value
    }

}