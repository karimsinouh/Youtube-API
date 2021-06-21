package com.example.youtubeapi.utils

import android.content.Context
import android.content.Intent

fun shareVideo(context: Context,videoId:String){
    val intent=Intent(Intent.ACTION_SEND)
    intent.type="text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=$videoId")
    context.startActivity(Intent.createChooser(intent,"Share video"))
}
