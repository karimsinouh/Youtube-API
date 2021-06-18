package com.example.youtubeapi.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.youtubeapi.api.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val youtube:Repository
):ViewModel() {
    val searchQuery= mutableStateOf("")
}