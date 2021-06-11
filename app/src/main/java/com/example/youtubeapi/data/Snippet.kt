package com.example.youtubeapi.data

import com.example.youtubeapi.data.thumbnails.Thumbnails
import java.io.Serializable

data class Snippet(
    val publishedAt:String,
    val title:String,
    val description:String,
    val thumbnails: Thumbnails?=null,
    val playlistId:String?=null,
    val position:Int?=null,
    val resourceId: ResourceId?=null
):Serializable