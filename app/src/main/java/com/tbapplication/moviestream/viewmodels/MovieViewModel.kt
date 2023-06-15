package com.tbapplication.moviestream.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbapplication.moviestream.model.Movie
import com.tbapplication.moviestream.repositories.MovieDetailsRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieDetailsRepository = MovieDetailsRepository()
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    fun getMovieDetails() {
        viewModelScope.launch {
            try {
                val movieList = movieDetailsRepository.getMovieList()
                _movies.value = movieList
            } catch (e: Exception) {
                Log.d("TAG", "exception:: $e")
            }
        }
    }
}



