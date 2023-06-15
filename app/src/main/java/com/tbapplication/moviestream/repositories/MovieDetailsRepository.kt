package com.tbapplication.moviestream.repositories


import com.tbapplication.moviestream.model.Movie
import com.tbapplication.moviestream.utils.RetrofitClient


class MovieDetailsRepository {

    //Creating instance of the API service
    private val apiService = RetrofitClient.apiService


    //Below method is marked suspend so it can be called from coroutine scope so we can make API request using Retrofit's enqueue() method
    suspend fun getMovieList(): List<Movie> {
        return apiService.getMovieList()
    }
}