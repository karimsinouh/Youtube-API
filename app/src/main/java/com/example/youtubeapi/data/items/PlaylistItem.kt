package com.example.youtubeapi.data.items


import com.example.youtubeapi.data.ContentDetails
import com.example.youtubeapi.data.Snippet
import java.io.Serializable

data class PlaylistItem(
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id:String,
    val contentDetails: ContentDetails?=null,
)