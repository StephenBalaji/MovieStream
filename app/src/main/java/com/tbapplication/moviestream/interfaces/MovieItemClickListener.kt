package com.tbapplication.moviestream.interfaces

import android.widget.ImageView
import com.tbapplication.moviestream.model.Movie

interface MovieItemClickListener {
    fun onMovieClick(movie: Movie, movieImageView: ImageView)
}