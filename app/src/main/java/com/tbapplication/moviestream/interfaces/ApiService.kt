package com.tbapplication.moviestream.interfaces

import com.tbapplication.moviestream.model.Movie
import retrofit2.http.GET

interface ApiService {
    @GET("StephenBalaji/MovieStreamDB/movies")
    suspend fun getMovieList(): List<Movie>
}