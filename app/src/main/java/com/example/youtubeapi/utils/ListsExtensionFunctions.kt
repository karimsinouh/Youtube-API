package com.example.youtubeapi.utils

import com.example.youtubeapi.data.items.PlaylistItem

fun  List<PlaylistItem>.findPlaylistById(id:String):PlaylistItem?{
    var item:PlaylistItem?=null

        forEach {
           if (it.id==id) {
               item = it
           }
        }

    return item
}

