package com.example.youtubeapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {

    @Insert(entity = WatchLaterVideo::class)
    suspend fun addToWatchLater(item:WatchLaterVideo)

    @Delete(entity = WatchLaterVideo::class)
    suspend fun removeFromWatchLater(item:WatchLaterVideo)

    @Query(" SELECT EXISTS (SELECT 1 FROM watch_later WHERE videoId=:videId) ")
    suspend fun exists(videId:String):Boolean

    @Query("SELECT * FROM watch_later")
    suspend fun getAll():List<WatchLaterVideo>

}