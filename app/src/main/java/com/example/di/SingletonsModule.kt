package com.example.di

import com.example.api.BASE_URL
import com.example.api.YoutubeEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}