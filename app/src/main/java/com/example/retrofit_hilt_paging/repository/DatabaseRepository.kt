package com.example.retrofit_hilt_paging.repository

import androidx.room.Dao
import com.example.retrofit_hilt_paging.db.MoviesDao
import com.example.retrofit_hilt_paging.db.MoviesEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao: MoviesDao){
    fun getAllFavoriteList ()=dao.getAllMovies()
    suspend fun insertMovie(entity: MoviesEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MoviesEntity) = dao.deleteMovie(entity)
    suspend fun existMovie(id: Int) = dao.existMovie(id)
}