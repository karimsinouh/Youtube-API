package com.example.youtubeapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityUtility
    @Inject constructor(
        @ApplicationContext private val context: Context,
) {

    fun hasInternet():Boolean{

       val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

         return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

             val net=connectivityManager.activeNetwork
             val capabilities=connectivityManager.getNetworkCapabilities(net)

             if (capabilities==null)
                 false
             else
                 when{
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                     else->false
                 }

         }else{
             val netInfo=connectivityManager.activeNetworkInfo
             netInfo!=null && netInfo.isConnected
         }


    }
    
}