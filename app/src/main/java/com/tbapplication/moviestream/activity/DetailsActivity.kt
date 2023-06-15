package com.tbapplication.moviestream.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.tbapplication.moviestream.R
import com.tbapplication.moviestream.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {


    private lateinit var videoUrl: String
    private lateinit var binding: ActivityDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_details)
        initViews()
    }

    private fun initViews() {
        val title =
            intent.extras!!.getString(resources.getString(R.string.movie_title)) //Retrieving title from intent
        val imageUrl =
            intent.extras!!.getString(resources.getString(R.string.image_url)) //Retrieving image poser from intent
        val coverImageUrl =
            intent.extras!!.getString(resources.getString(R.string.cover_image_url)) //Retrieving cover image from intent

        videoUrl = intent.extras!!.getString(resources.getString(R.string.video_url))
            .toString() //Retrieving video URL from intent

        Glide.with(this).load(imageUrl)
            .into(binding.detailMovieImg) //Fetching poster image from URL and adding it to image view

        Glide.with(this).load(coverImageUrl)
            .into(binding.detailMovieimgCover) //Fetching cover image from URL and adding it to image view

        binding.detailMovieTitle.text = title // Updating movie title
        binding.detailMovieimgCover.animation =
            AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        binding.playfab.animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)

        binding.playfab.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra(resources.getString(R.string.video_url), videoUrl)
            startActivity(intent) // Navigating to player activity to play video
        }
    }
}