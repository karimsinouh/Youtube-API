package com.example.youtubeapi.ui.viewPlaylist

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.youtubeapi.data.items.PlaylistItem
@Composable
fun ViewPlaylist(
    playlist:PlaylistItem,
    vm:ViewPlaylistViewModel,
){
    Text(text = playlist.snippet.title)
}