package com.example.youtubeapi.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="watch_later")
data class WatchLaterVideo (
    val videoId:String,
    val thumbnail:String,
    val title:String,
    @PrimaryKey(autoGenerate = true) val id:Int
)