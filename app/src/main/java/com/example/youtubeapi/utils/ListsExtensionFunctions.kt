package com.example.youtubeapi.utils

import androidx.compose.runtime.MutableState
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

fun <T> MutableState<List<T>?>.addAll(list: List<T>?){
    val temp=ArrayList(value?: emptyList())
    temp.addAll(list?: emptyList())
    value=temp
}