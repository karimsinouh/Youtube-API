package com.example.youtubeapi.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val youtube:Repository
):ViewModel() {

    val searchQuery= mutableStateOf("")
    val items= mutableStateListOf<SearchItem>()
    val isLoading= mutableStateOf(false)
    val message= mutableStateOf<String?>(null)
    var pageToken=""

    fun search()=viewModelScope.launch{

        if(searchQuery.value=="")
            return@launch

        isLoading.value=true
        youtube.search(searchQuery.value,pageToken){
            isLoading.value=false
            if (it.isSuccessful){
                val result=it.data?.items?: emptyList()
                items.addAll(result)
                pageToken=it.data?.nextPageToken?:""

                if(result.isEmpty()){
                    message.value="We couldn't find any result matching ${searchQuery.value}"
                }

            }else
                message.value=it.message

        }
    }

}