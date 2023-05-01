package com.example.retrofit_hilt_paging.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.retrofit_hilt_paging.model.MovieDetail
import com.example.retrofit_hilt_paging.model.MovieList
import com.example.retrofit_hilt_paging.paging.PagingSource
import com.example.retrofit_hilt_paging.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: ApiRepository):ViewModel(){
    val loading=MutableLiveData<Boolean>()

    val movielist=Pager(PagingConfig(1)){
        PagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    //Api
    val detailsMovie=MutableLiveData<MovieList>()
    fun loadDetailsMovie(page:Int)=viewModelScope.launch {
        loading.postValue(true)
        val response=repository.getPopulerMovieList(page)
        if (response.isSuccessful){
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }

}