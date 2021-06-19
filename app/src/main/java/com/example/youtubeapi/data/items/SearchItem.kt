package com.example.youtubeapi.data.items

import com.example.youtubeapi.data.ResourceId
import com.example.youtubeapi.data.Snippet

data class SearchItem (
    val kind:String,
    val etag:String,
    val snippet: Snippet,
    val id: ResourceId,
){
    fun asPlaylistItem():PlaylistItem{
        return PlaylistItem(kind,etag,snippet,id.playlistId?:"")
    }

}