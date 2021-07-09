package com.example.youtubeapi.api

import com.example.youtubeapi.data.ResponsePage
import com.example.youtubeapi.data.items.VideoItem
import com.example.youtubeapi.data.Result
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.data.items.SearchItem
import com.example.youtubeapi.utils.ConnectivityUtility
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api:YoutubeEndPoint,
    private val network:ConnectivityUtility
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
        if (network.hasInternet()){
            try{
                api.getVideos(pageToken).apply {
                    listener(Result(isSuccessful,body(),message()))
                }
            }catch (e:IOException){
                listener(Result(false,null,e.message))
            }
        }else{
            listener(Result(false,null,"Please check your internet connection"))
        }
    }

    suspend fun getPlaylists(pageToken:String?, listener:(Result<ResponsePage<PlaylistItem>>)->Unit){
        if (network.hasInternet()){
            try {
                api.getPlaylists(pageToken).apply {
                    listener(Result(isSuccessful,body(),message()))
                }
            }catch (e:IOException){
                listener(Result(false,null,e.message))
            }
        }else{
            listener(Result(false,null,"Please check your internet connection"))
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