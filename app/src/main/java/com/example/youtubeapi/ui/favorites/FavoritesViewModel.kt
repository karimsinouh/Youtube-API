package com.example.youtubeapi.ui.favorites

import androidx.lifecycle.ViewModel
import com.example.youtubeapi.data.screen.ScreenState
import com.example.youtubeapi.ui.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() :ViewModel() {
    val screenState = ScreenState.getInstance<Screen.Favorites>()



}