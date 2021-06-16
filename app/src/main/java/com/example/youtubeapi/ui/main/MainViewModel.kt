package com.example.youtubeapi.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.data.screen.*
import com.example.youtubeapi.database.DAO
import com.example.youtubeapi.database.WatchLaterVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository,
    private val db:DAO,
): ViewModel() {

    val videosState= ScreenState.getInstance<VideoItem>()
    val playlistsState= ScreenState.getInstance<PlaylistItem>()


    init {
        viewModelScope.launch {
            loadVideos()
            loadPlaylists()
        }
    }

    suspend fun loadVideos(){
            videosState.setLoading(true)
            delay(1000)
            repo.getVideos(videosState.nextPageToken){

                videosState.setLoading(false)

                if (it.isSuccessful){
                    videosState.addItems(it.data?.items)
                    videosState.nextPageToken=it.data?.nextPageToken ?: ""
                }else
                    videosState.setMessage(it.message)

            }
    }

    suspend fun loadPlaylists() {

        playlistsState.setLoading(true)
        delay(1000)

        repo.getPlaylists(playlistsState.nextPageToken){
            playlistsState.setLoading(false)
            if (it.isSuccessful){
                playlistsState.nextPageToken=it.data?.nextPageToken ?: ""
                playlistsState.addItems(it.data?.items)
            }else
                playlistsState.setMessage(it.message)

        }
    }

    val watchLater=db.getAll()

}
