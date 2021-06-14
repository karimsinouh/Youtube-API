package com.example.youtubeapi.utils

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.edit

fun Context.isDarkMode():Boolean{
    val prefs=getSharedPreferences("darkMode",Context.MODE_PRIVATE)
    return prefs.getBoolean("enabled",false)
}

fun Context.setDarkMode(value:Boolean){
    val prefs=getSharedPreferences("darkMode",Context.MODE_PRIVATE)
    prefs.edit {
        putBoolean("enabled",value)
        commit()
    }
}

private fun Context.isSystemDarkMode():Boolean{
    val mode=resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
    return mode== Configuration.UI_MODE_NIGHT_YES
}