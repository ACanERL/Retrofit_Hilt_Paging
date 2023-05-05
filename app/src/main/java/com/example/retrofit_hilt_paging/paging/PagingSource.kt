package com.example.retrofit_hilt_paging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofit_hilt_paging.model.MovieList
import com.example.retrofit_hilt_paging.repository.ApiRepository
import retrofit2.HttpException

class PagingSource(
    private val repository: ApiRepository ,
) : PagingSource<Int, MovieList.Result>() {
    override fun getRefreshKey(state: PagingState<Int, MovieList.Result>): Int? {
      return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieList.Result> {
        return try {
            val currentPage = params.key ?: 2
            val response = repository.getPopulerMovieList(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<MovieList.Result>()
            responseData.addAll(data)


            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}