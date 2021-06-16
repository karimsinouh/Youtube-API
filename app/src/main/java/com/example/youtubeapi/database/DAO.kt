package com.example.youtubeapi.database

import androidx.lifecycle.LiveData
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
    fun exists(videId:String):LiveData<Boolean>

    @Query("SELECT * FROM watch_later")
    fun getAll():LiveData<List<WatchLaterVideo>>

    @Query("SELECT * FROM watch_later WHERE videoId=:videoId")
    suspend fun getItem(videoId:String):WatchLaterVideo

}