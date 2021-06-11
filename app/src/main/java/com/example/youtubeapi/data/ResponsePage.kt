package com.example.youtubeapi.data

data class ResponsePage <T> (
    val nextPageToken:String?="",
    val etag:String,
    val kind:String,
    val items:List<T>,
    val pageInfo: PageInfo,
    )
