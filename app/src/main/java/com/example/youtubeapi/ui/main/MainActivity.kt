package com.example.youtubeapi.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import com.example.youtubeapi.ui.theme.YoutubeAPITheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeAPITheme {

                window.statusBarColor= Color.parseColor("#ffffff")

                Surface(color = MaterialTheme.colors.background) {

                }

            }
        }
    }
}
