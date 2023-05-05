package com.example.retrofit_hilt_paging.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit_hilt_paging.db.MoviesEntity
import com.example.retrofit_hilt_paging.model.MovieDetail
import com.example.retrofit_hilt_paging.repository.ApiRepository
import com.example.retrofit_hilt_paging.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MovieDetailViewModel @Inject constructor(private var repository: ApiRepository,private val databaseRepository: DatabaseRepository) :ViewModel(){
    val detailsMovie = MutableLiveData<MovieDetail>()
    val loading = MutableLiveData<Boolean>()

    fun getDetail(id:Int)=viewModelScope.launch {
        val response=repository.getMovieDetails(id)
      if(response.isSuccessful){
            detailsMovie.postValue(response.body())
           loading.postValue(true)
      }else{
          loading.postValue(false)
      }
    }

    //Database
    val isFavorite = MutableLiveData<Boolean>()
    suspend fun existMovie(id:Int)= withContext(viewModelScope.coroutineContext){
        databaseRepository.existMovie(id)
    }

    fun favoriteMovie(id:Int, entity: MoviesEntity)=viewModelScope.launch {
        val exists= databaseRepository.existMovie(id)
        if (exists){
            isFavorite.postValue(false)
            databaseRepository.deleteMovie(entity)
        }else{
            isFavorite.postValue(true)
            databaseRepository.insertMovie(entity)
        }
    }
}