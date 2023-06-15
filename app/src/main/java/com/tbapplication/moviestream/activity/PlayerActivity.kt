package com.tbapplication.moviestream.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.tbapplication.moviestream.R
import com.tbapplication.moviestream.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {


    private lateinit var player: ExoPlayer
    private lateinit var videoUrl: String
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_player)

        val trackSelector = DefaultTrackSelector(this)

        player = ExoPlayer.Builder(this).setTrackSelector(trackSelector).build() // Creating exoplayer with default track selector
        binding.playerView.player = player // assigning ExoPlayer to PlayerView

        videoUrl = intent.extras!!.getString(resources.getString(R.string.video_url)).toString() //Retrieving video URL from intent

        val mediaItem: MediaItem =
            MediaItem.fromUri(videoUrl)

        player.addMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true


    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}