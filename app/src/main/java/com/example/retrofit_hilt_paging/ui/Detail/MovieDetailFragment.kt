package com.example.retrofit_hilt_paging.ui.Detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.retrofit_hilt_paging.databinding.FragmentMovieDetailBinding
import com.example.retrofit_hilt_paging.db.MoviesEntity

import com.example.retrofit_hilt_paging.util.Constants
import com.example.retrofit_hilt_paging.util.showInvisible
import com.example.retrofit_hilt_paging.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var movieId = 0
    @Inject
    lateinit var entity: MoviesEntity
    private lateinit var binding: FragmentMovieDetailBinding
    private val detailsViewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0) {
            detailsViewModel.getDetail(movieId)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {

            detailsViewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    progressBar.showInvisible(false)
                } else {
                    progressBar.showInvisible(true)
                }
            }
                detailsViewModel.detailsMovie.observe(viewLifecycleOwner, Observer { response->
                    val moviePosterURL = Constants.POSTER_BASE_URL + response.posterPath
                   binding.detailImage.load(moviePosterURL){
                       crossfade(true)
                       crossfade(800)
                   }
                    detailName.text=response.title
                    detailTitle.text=response.overview
                    detailDate.text=response.releaseDate
                    detailScore.text=response.status

                    binding.button.setOnClickListener {
                        entity.id=movieId
                        entity.title=response.title
                        entity.year=response.releaseDate
                        entity.lang=response.originalLanguage
                        entity.poster=response.posterPath
                        entity.rate=response.voteAverage.toString()

                        detailsViewModel.favoriteMovie(movieId,entity)
                    }
                })


        }
    }
}