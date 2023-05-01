package com.example.retrofit_hilt_paging.repository

import androidx.lifecycle.MutableLiveData
import com.example.retrofit_hilt_paging.api.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(private  val apiService: ApiService) {
    suspend fun getPopulerMovieList(page:Int)=apiService.getPopularMoviesList(page)
    suspend fun getMovieDetails(id:Int)=apiService.getMovieDetails(id)
}