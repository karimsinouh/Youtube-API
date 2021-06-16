package com.example.youtubeapi.data.items

import com.example.youtubeapi.data.ContentDetails
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.Statistics
import com.example.youtubeapi.database.WatchLaterVideo


data class VideoItem (
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id:String?=null,
    val contentDetails: ContentDetails?=null,
    val statistics: Statistics?=null
){
    fun toWatchLaterItem():WatchLaterVideo{
        return WatchLaterVideo(id!!,snippet.thumbnails?.medium?.url!!,snippet.title,0)
    }

}
