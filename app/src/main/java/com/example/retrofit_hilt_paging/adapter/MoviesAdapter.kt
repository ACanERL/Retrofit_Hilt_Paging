package com.example.retrofit_hilt_paging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofit_hilt_paging.R
import com.example.retrofit_hilt_paging.databinding.MovieItemBinding
import com.example.retrofit_hilt_paging.model.MovieList
import com.example.retrofit_hilt_paging.util.Constants.POSTER_BASE_URL
import javax.inject.Inject

class MoviesAdapter @Inject constructor():PagingDataAdapter<MovieList.Result,MoviesAdapter.ViewHolder>(differCallback) {
    private lateinit var binding: MovieItemBinding
    private lateinit var context: Context

    inner class ViewHolder:RecyclerView.ViewHolder(binding.root){

        fun bind(item:MovieList.Result){
            binding.apply {
                movieName.text=item.title

                val moviePosterURL = POSTER_BASE_URL + item?.posterPath
                movieImage.load(moviePosterURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }

    }


    companion object {
        val differCallback = object : DiffUtil.ItemCallback<MovieList.Result>() {
            override fun areItemsTheSame(oldItem: MovieList.Result, newItem: MovieList.Result): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: MovieList.Result, newItem: MovieList.Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickListener: ((MovieList.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieList.Result) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = MovieItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }
}