package com.example.youtubeapi.ui.watchLater

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.youtubeapi.ui.main.MainViewModel

@Composable
fun Favorites(vm:MainViewModel){
    LazyColumn {
        items(vm.watchLater.value){
            Text(it.title)
        }
    }
}