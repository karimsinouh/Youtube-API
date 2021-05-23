package com.example.youtubeapi.data.items

import com.karimsinouh.youtixv2.data.ContentDetails
import com.karimsinouh.youtixv2.data.Snippet
import java.io.Serializable

data class PlaylistItem(
    val kind:String,
    val etag:String,
    val snippet:Snippet,
    val id:String,
    val contentDetails: ContentDetails,
):Serializable