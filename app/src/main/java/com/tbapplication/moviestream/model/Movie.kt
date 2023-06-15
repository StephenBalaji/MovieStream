package com.tbapplication.moviestream.model

data class Movie(
    val cast: List<String>,
    val description: String,
    val director: String,
    val duration: String,
    val genre: String,
    val id: Int,
    val release_year: Int,
    val thumbnail: String,
    val title: String,
    val video_url: String,
    var banner_image: String
)