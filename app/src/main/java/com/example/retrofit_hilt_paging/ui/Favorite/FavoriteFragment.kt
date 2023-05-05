package com.example.retrofit_hilt_paging.ui.Favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_hilt_paging.adapter.FavoriteMovieAdapter
import com.example.retrofit_hilt_paging.databinding.FragmentFavoriteBinding
import com.example.retrofit_hilt_paging.viewmodel.DatabaseViewModel
import com.example.retrofit_hilt_paging.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: MovieViewModel by viewModels()
    @Inject
    lateinit var favoriteMoviesAdapter: FavoriteMovieAdapter

    private val databaseViewModel: DatabaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
                    databaseViewModel.loadFavoriteMovieList()
            databaseViewModel.favoriteMovieList.observe(viewLifecycleOwner, Observer { favoriteList->
                    favoriteMoviesAdapter.bind(favoriteList)
                    favoriteRecycler.layoutManager=LinearLayoutManager(requireContext())
                    favoriteRecycler.adapter=favoriteMoviesAdapter
            })

            favoriteMoviesAdapter.setonItemClickListener {
                    val directions=
                        FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailFragment(it.id)
                    findNavController().navigate(directions)
            }
        }
    }
}