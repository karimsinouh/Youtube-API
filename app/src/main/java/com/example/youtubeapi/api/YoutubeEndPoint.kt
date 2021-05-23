package com.example.youtubeapi.api

import com.example.youtubeapi.data.ResponsePage
import com.example.youtubeapi.data.items.PlaylistItem
import com.example.youtubeapi.data.items.SearchItem
import com.example.youtubeapi.data.items.VideoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeEndPoint {

    @GET("playlistItems?part=snippet&playlistId=$PLAYLIST_ID&maxResults=20&key=$API_KEY")
    suspend fun getVideos( @Query("pageToken") pageToken:String?="" ): Response<ResponsePage<VideoItem>>

    @GET("playlists?part=snippet,contentDetails&channelId=$CHANNEL_ID&maxResults=20&key=$API_KEY")
    suspend fun getPlaylists( @Query("pageToken") pageToken:String?="" ):Response<ResponsePage<PlaylistItem>>

    @GET("search?part=snippet&channelId=$CHANNEL_ID&key=$API_KEY&maxResults=8")
    suspend fun search( @Query("q") q:String,@Query("pageToken") pageToken:String?="" ):Response<ResponsePage<SearchItem>>

    @GET("videos?part=snippet&key=$API_KEY")
    suspend fun getSelectedVideos( @Query("id") ids:String ):Response<ResponsePage<VideoItem>>

    @GET("videos?part=snippet,contentDetails,statistics&key=$API_KEY")
    suspend fun getVideo( @Query("id") id:String ):Response<ResponsePage<VideoItem>>

    @GET("playlistItems?part=snippet,contentDetails&key=$API_KEY&maxResults=15")
    suspend fun getPlaylistVideos( @Query("playlistId") id:String, @Query("pageToken") token:String?="" ):Response<ResponsePage<VideoItem>>

}