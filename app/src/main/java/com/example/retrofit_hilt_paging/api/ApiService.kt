package com.example.retrofit_hilt_paging.api

import com.example.retrofit_hilt_paging.model.MovieDetail
import com.example.retrofit_hilt_paging.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMoviesList(
        @Query("page") page: Int,
    ): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend

    fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetail>
}