package com.example.youtubeapi.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.youtubeapi.R

sealed class Screen(
    val route:String,
    @StringRes val title:Int,
    @DrawableRes val icon:Int?=null,
    val position:Int?=null
    ){


    object Items{

        val rowItems = listOf(Videos,Playlists,WatchLater)

        val drawerItems= listOf(Videos,Playlists,WatchLater)

        val all=listOf(
            Videos,
            Playlists,
            WatchLater,
            ViewVideo,
            ViewPlaylist
        )
    }

    object Videos:Screen("videos", R.string.videos, R.drawable.ic_videos,0)
    object Playlists:Screen("playlists", R.string.playlists, R.drawable.ic_playlist,1)
    object WatchLater:Screen("watchlater", R.string.favorites, R.drawable.ic_favorite,2)
    object ViewVideo:Screen("viewVideo", R.string.videos,position = 4)
    object ViewPlaylist:Screen("viewPlaylist", R.string.playlists,position = 5)

}
fun findByRoute(route:String):Screen{
    var screen:Screen= Screen.Videos

    for(it in Screen.Items.all){
        if (route.contains(it.route))
            screen=it
    }

    return screen
}
