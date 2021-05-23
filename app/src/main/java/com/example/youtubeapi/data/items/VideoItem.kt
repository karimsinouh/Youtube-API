package com.example.youtubeapi.data.items

import com.example.youtubeapi.data.ContentDetails
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.Statistics


data class VideoItem (
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id:String?=null,
    val contentDetails: ContentDetails?=null,
    val statistics: Statistics?=null
)
