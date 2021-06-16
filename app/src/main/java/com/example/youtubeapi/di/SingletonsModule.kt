package com.example.youtubeapi.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.youtubeapi.api.BASE_URL
import com.example.youtubeapi.api.YoutubeEndPoint
import com.example.youtubeapi.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonsModule {

    @Provides
    @Singleton
    fun retrofit()=
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()

    @Provides
    @Singleton
    fun api(retrofit: Retrofit)=retrofit.create(YoutubeEndPoint::class.java)

    @Provides
    @Singleton
    fun database(@ApplicationContext c:Context)= Room.databaseBuilder(c,Database::class.java,"database").build()

    @Provides
    @Singleton
    fun dao(db:Database)=db.dao()

}