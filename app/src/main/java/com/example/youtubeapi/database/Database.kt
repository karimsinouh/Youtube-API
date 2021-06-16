package com.example.youtubeapi.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WatchLaterVideo::class],version = 1)
abstract class Database:RoomDatabase() {
    abstract fun dao():DAO
}