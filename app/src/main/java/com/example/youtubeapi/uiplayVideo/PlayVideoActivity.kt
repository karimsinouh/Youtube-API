package com.example.youtubeapi.uiplayVideo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.example.youtubeapi.R
import com.example.youtubeapi.api.API_KEY
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class PlayVideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var videoId:String
    private lateinit var playerView:YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_YoutubeAPI)
        setContentView(R.layout.activity_play_video)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        videoId=intent.getStringExtra("videoId")!!
        playerView=findViewById(R.id.player)

        playerView.initialize(API_KEY,this)

    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        p2: Boolean
    ) {
        youTubePlayer?.loadVideo(videoId)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
    }

    companion object{
        fun open(c: Context, videoId:String){
            val i= Intent(c,PlayVideoActivity::class.java)
            i.putExtra("videoId",videoId)
            c.startActivity(i)
        }
    }

}