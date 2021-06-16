package com.example.youtubeapi.ui.viewVideo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.database.DAO
import com.example.youtubeapi.database.WatchLaterVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewVideoViewModel @Inject constructor(
    private val repo:Repository,
    private val db:DAO,
):ViewModel() {

    val video= mutableStateOf<VideoItem?>(null)
    val message= mutableStateOf<String?>(null)

    fun loadVideo(id:String)=viewModelScope.launch{
        delay(1000)
        repo.getVideo(id){
            if (it.isSuccessful)
                setVideo(it.data)
            else
                setMessage(it.message)
        }
    }

    fun setVideo(_video:VideoItem?){
        video.value=_video
    }

    fun setMessage(value:String?){
        message.value=value
    }

    fun exists(videoId:String)=db.exists(videoId)

    private suspend fun addToWatchLater(){
        val item=video.value?.toWatchLaterItem()
        db.addToWatchLater(item!!)
    }

    private suspend fun removeFromWatchLater(id:String){
        val item=db.getItem(id)
        db.removeFromWatchLater(item)
    }

    fun onWatchLater(id:String,added:Boolean)=viewModelScope.launch{
        if (added)
            addToWatchLater()
        else
            removeFromWatchLater(id)
    }

}