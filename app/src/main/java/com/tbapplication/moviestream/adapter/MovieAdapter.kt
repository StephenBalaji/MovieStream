package com.tbapplication.moviestream.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tbapplication.moviestream.R
import com.tbapplication.moviestream.interfaces.MovieItemClickListener
import com.tbapplication.moviestream.model.Movie

class MovieAdapter(
    private var context: Context,
    mData: List<Movie>,
    listener: MovieItemClickListener
) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    var mData: List<Movie>
    var movieItemClickListener: MovieItemClickListener

    init {
        this.mData = mData
        movieItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleView.text = mData[position].title // Updating title
        loadImageFromUrl(mData[position].thumbnail, holder.image) // Updating image to image view
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView
        var image: ImageView

        init {
            titleView = itemView.findViewById(R.id.item_movie_title)
            image = itemView.findViewById(R.id.item_movie_img)

            itemView.setOnClickListener {
                movieItemClickListener.onMovieClick(
                    mData[adapterPosition],
                    image
                )
            }
        }
    }


    //This method is used to update movie list from root activity
    fun setMovieList(movieList: List<Movie>) {
        mData = movieList
        notifyDataSetChanged()
    }

    //This method is used to retrieve image from URL and assign it to give image view
    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions())
            .into(imageView)
    }
}