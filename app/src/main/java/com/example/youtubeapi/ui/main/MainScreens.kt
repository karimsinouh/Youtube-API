package com.example.youtubeapi.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.youtubeapi.R

sealed class Screen(
    val route:String,
    @StringRes val title:Int,
    @DrawableRes icon:Int,
    val position:Int
    ){

    object Videos:Screen("videos", R.string.videos, R.drawable.ic_videos,0)
    object Playlists:Screen("playlists", R.string.playlists, R.drawable.ic_playlist,1)
    object Favorites:Screen("favorites", R.string.favorites, R.drawable.ic_favorite,2)
    object Downloads:Screen("downloads", R.string.doanloads, R.drawable.ic_download,3)

    object All{
        val list = listOf(
            Videos,Playlists,Favorites,Downloads
        )
    }

}
