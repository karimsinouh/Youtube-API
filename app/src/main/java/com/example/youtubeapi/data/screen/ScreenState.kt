package com.example.youtubeapi.data.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ScreenState <T>(
    val isLoading:MutableState<Boolean>,
    val items:MutableState<List<T>>,
    val message:MutableState<String?>,
    var nextPageToken:String?="",
){

    companion object{
        fun <T>getInstance():ScreenState<T>{
            return ScreenState(
                mutableStateOf(true),
                mutableStateOf(emptyList()),
                mutableStateOf(null),
                "",
            )
        }
    }

    fun addItems(_items:List<T>?){
        val array=ArrayList(items.value)
        array.addAll(_items?: emptyList())
        items.value=array
    }

    fun setLoading(_isLoading:Boolean){
        isLoading.value=_isLoading
    }

    fun setMessage(_message:String?){
        message.value=_message
    }

    val isLoadingMore=isLoading.value && nextPageToken!=""

    val canLoadMore= nextPageToken!="" && items.value.isNotEmpty() || nextPageToken=="" && items.value.isEmpty()

}