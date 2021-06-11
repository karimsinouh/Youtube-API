package com.example.youtubeapi.ui.main

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.youtubeapi.R
import java.lang.NullPointerException

sealed class Screen(
    val route:String,
    @StringRes val title:Int,
    @DrawableRes icon:Int?=null,
    val position:Int?=null
    ){


    object Items{
        val rowItems = listOf(Videos,Playlists,WatchLater,Downloads)
        val all=listOf(
            Videos,
            Playlists,
            WatchLater,
            Downloads,
            ViewVideo,
            ViewPlaylist
        )
    }

    object Videos:Screen("videos", R.string.videos, R.drawable.ic_videos,0)
    object Playlists:Screen("playlists", R.string.playlists, R.drawable.ic_playlist,1)
    object WatchLater:Screen("watchlater", R.string.favorites, R.drawable.ic_favorite,2)
    object Downloads:Screen("downloads", R.string.doanloads, R.drawable.ic_download,3)
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
