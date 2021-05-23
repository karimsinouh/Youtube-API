package com.example.api

import com.example.data.ResponsePage
import com.example.data.items.VideoItem
import com.example.data.Result
import com.example.data.items.PlaylistItem
import com.example.data.items.SearchItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api:YoutubeEndPoint
){

    suspend fun getVideo(id:String, listener:(Result<VideoItem>)->Unit ){
        api.getVideo(id).apply {
            listener(
                if (isSuccessful)
                    if (body()?.items?.isNotEmpty()!!)
                        Result(true,body()?.items!![0],message())
                    else
                        Result(false,null,"This video is not available")
                else
                    Result(false,null,message())

            )
        }
    }

    suspend fun getSelectedVideos(id:String, listener:(Result<List<VideoItem>>)->Unit ){
        api.getSelectedVideos(id).apply {
            listener(Result(isSuccessful,body()?.items,message()))
        }
    }

    suspend fun getVideos(pageToken:String?, listener:(Result<ResponsePage<VideoItem>>)->Unit){
        api.getVideos(pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }

    suspend fun getPlaylists(pageToken:String, listener:(Result<ResponsePage<PlaylistItem>>)->Unit){
        api.getPlaylists(pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }

    suspend fun getPlaylistVideos(playlistId:String,pageToken: String,listener:(Result<ResponsePage<VideoItem>>)->Unit){
        api.getPlaylistVideos(playlistId,pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }

    suspend fun search(q:String,pageToken: String?,listener:(Result<ResponsePage<SearchItem>>)->Unit){
        api.search(q,pageToken).apply {
            listener(Result(isSuccessful,body(),message()))
        }
    }


}