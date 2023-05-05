package com.example.retrofit_hilt_paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofit_hilt_paging.databinding.MovieItemBinding
import com.example.retrofit_hilt_paging.db.MoviesEntity
import com.example.retrofit_hilt_paging.util.Constants.POSTER_BASE_URL
import javax.inject.Inject

class FavoriteMovieAdapter @Inject constructor():RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>(){
    private lateinit var binding: MovieItemBinding
    var moviesList = emptyList<MoviesEntity>()
    private var onItemClickListener : ((MoviesEntity) -> Unit)? = null
    fun setonItemClickListener(listener: (MoviesEntity) -> Unit) {
        onItemClickListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieAdapter.ViewHolder {
        binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapter.ViewHolder, position: Int) {
        holder.setData(moviesList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int =moviesList.size


    inner class ViewHolder():RecyclerView.ViewHolder(binding.root){

        fun setData(item:MoviesEntity){
            val moviePosterURL = POSTER_BASE_URL + item.poster
                binding.apply {
                  movieName.text=item.title
                    movieImage.load(moviePosterURL){
                        crossfade(true)
                        crossfade(800)
                        scale(Scale.FIT)
                    }

                    root.setOnClickListener {
                            onItemClickListener.let {
                                if (it != null) {
                                    it(item)
                                }
                            }
                    }
                }
        }
    }

    fun bind(data:List<MoviesEntity >){
        val moviesDiffUtils = MoviesDiffUtils(moviesList,data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        moviesList=data
        diffUtils.dispatchUpdatesTo(this)
    }

    //callback
    class MoviesDiffUtils(private val oldItem:List<MoviesEntity>, private val newItem:List<MoviesEntity>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // === data type is compred here
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}