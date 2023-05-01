package com.example.retrofit_hilt_paging.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_hilt_paging.R
import com.example.retrofit_hilt_paging.adapter.MoviesAdapter
import com.example.retrofit_hilt_paging.databinding.FragmentMovieListBinding
import com.example.retrofit_hilt_paging.util.API_KEY
import com.example.retrofit_hilt_paging.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    @Inject
    lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      viewModel.loadDetailsMovie(1)

        viewModel.detailsMovie.observe(viewLifecycleOwner, Observer {
            it.results.forEach {
                println("Movie Name:"+it.title)
            }
        })

        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.movielist.collect{
                    moviesAdapter.submitData(it)
                }
            }
        }

        moviesAdapter.setOnItemClickListener {
            val directions=MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment()
            findNavController().navigate(directions)
        }

        lifecycleScope.launchWhenCreated {
            moviesAdapter.loadStateFlow.collect{
                val state=it.refresh
                binding.prgBarMovies.isVisible=state is LoadState.Loading
            }
        }

        binding.movieListRecycler.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=moviesAdapter
        }
    }
}