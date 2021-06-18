package com.example.youtubeapi.ui.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.api.Repository
import com.example.youtubeapi.data.items.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
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
        isLoading.value=true
        youtube.search(searchQuery.value,pageToken){
            isLoading.value=false
            if (it.isSuccessful){
                items.addAll(it.data?.items?: emptyList())
                pageToken=it.data?.nextPageToken?:""
            }else
                message.value=it.message

        }
    }


}