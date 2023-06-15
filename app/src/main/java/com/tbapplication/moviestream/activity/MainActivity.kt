package com.tbapplication.moviestream.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tbapplication.moviestream.R
import com.tbapplication.moviestream.adapter.MovieAdapter
import com.tbapplication.moviestream.adapter.SliderPagerAdapter
import com.tbapplication.moviestream.databinding.ActivityMainBinding
import com.tbapplication.moviestream.interfaces.MovieItemClickListener
import com.tbapplication.moviestream.model.Movie
import com.tbapplication.moviestream.model.Slider
import com.tbapplication.moviestream.viewmodels.MovieViewModel
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity(), MovieItemClickListener {

    private lateinit var userViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var watchAgainMovieAdapter: MovieAdapter

    private lateinit var trendingRecyclerView: RecyclerView
    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var watchAgainRecyclerView: RecyclerView

    private lateinit var trendingTextView: TextView
    private lateinit var popularTextView: TextView
    private lateinit var watchAgainTextView: TextView

    private var movieList: List<Movie> = listOf()
    private lateinit var slideList: ArrayList<Slider>

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()

    }

    private fun initViews() {
        // assigning recycler view for trending, popular and watch again views
        trendingRecyclerView = binding.trending.root.findViewById(R.id.Rv_movies)
        popularRecyclerView = binding.popular.root.findViewById(R.id.Rv_movies)
        watchAgainRecyclerView = binding.watchAgain.root.findViewById(R.id.Rv_movies)


        // assigning title view for trending, popular and watch again views
        trendingTextView = binding.trending.root.findViewById(R.id.movie_type)
        popularTextView = binding.popular.root.findViewById(R.id.movie_type)
        watchAgainTextView = binding.watchAgain.root.findViewById(R.id.movie_type)


        //initializing and populating slider list
        slideList = ArrayList()
        slideList.add(Slider(R.drawable.gg, resources.getString(R.string.guardians)))
        slideList.add(Slider(R.drawable.fastfurious, resources.getString(R.string.fast_furious)))
        slideList.add(Slider(R.drawable.spiderman, resources.getString(R.string.spider_man)))
        slideList.add(Slider(R.drawable.johnwick, resources.getString(R.string.john_wick)))

        val adapter = SliderPagerAdapter(this, slideList) // creating instance of slider adapter
        binding.sliderPager.adapter = adapter // assigning adapter to slider viewPager

        val timer = Timer()
        timer.scheduleAtFixedRate(SlideTimer(), 4000, 6000)

        binding.indicator.setupWithViewPager(binding.sliderPager, true)

        userViewModel =
            ViewModelProvider(this)[MovieViewModel::class.java] // creating instance of movie view model
        userViewModel.movies.observe(this) {

            // Update UI with the movie list
            movieList = it
            movieAdapter.setMovieList(movieList) // calling this method to update trending movie list UI

            val popularMovieList = it.toMutableList()
            popularMovieList.shuffle() // since we using same data for popular movie list, here we just shuffling it before updating it to UI
            popularMovieAdapter.setMovieList(popularMovieList) // calling this method to update popular movie list UI

            val watchAgainMovieList = it.toMutableList()
            watchAgainMovieList.shuffle() // since we using same data for watch again movie list, here we just shuffling it before updating it to UI
            watchAgainMovieAdapter.setMovieList(watchAgainMovieList) // calling this method to update watch again movie list UI

        }
        userViewModel.getMovieDetails()

        //Creating a instance of movie adapter and assigning it to trending movie recycler view
        movieAdapter = MovieAdapter(this, movieList, this)
        trendingRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        trendingRecyclerView.adapter = movieAdapter

        // Creating a instance of movie adapter and assigning it to popular movie recycler view
        popularMovieAdapter = MovieAdapter(this, movieList, this)
        popularRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularRecyclerView.adapter = popularMovieAdapter

        //Creating a instance of movie adapter and assigning it to watch again movie recycler view
        watchAgainMovieAdapter = MovieAdapter(this, movieList, this)
        watchAgainRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        watchAgainRecyclerView.adapter = watchAgainMovieAdapter

        trendingTextView.text =
            resources.getString(R.string.trending_now) // setting title for the text view above trending recycler view
        popularTextView.text =
            resources.getString(R.string.popular_now) // setting title for the text view above popular recycler view
        watchAgainTextView.text =
            resources.getString(R.string.watch_again) // setting title for the text view above watch again recycler view


    }


    //Handling card click event to navigate user ot movie details page
    override fun onMovieClick(movie: Movie, movieImageView: ImageView) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(resources.getString(R.string.movie_title), movie.title)
        intent.putExtra(resources.getString(R.string.image_url), movie.thumbnail)
        intent.putExtra(resources.getString(R.string.cover_image_url), movie.banner_image)
        intent.putExtra(resources.getString(R.string.video_url), movie.video_url)
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this@MainActivity,
            movieImageView,
            resources.getString(R.string.animate_image)
        )
        startActivity(intent, options.toBundle())
    }


    //This class is used to change current view pager position with given time delay
    inner class SlideTimer : TimerTask() {

        override fun run() {
            runOnUiThread {
                if (binding.sliderPager.currentItem < slideList.size - 1) {
                    binding.sliderPager.currentItem = binding.sliderPager.currentItem + 1
                } else {
                    binding.sliderPager.currentItem = 0
                }
            }
        }
    }
}

