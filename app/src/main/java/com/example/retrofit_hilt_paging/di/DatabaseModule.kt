package com.example.retrofit_hilt_paging.di

import android.content.Context
import androidx.room.Room
import com.example.retrofit_hilt_paging.db.MoviesDatabase
import com.example.retrofit_hilt_paging.db.MoviesEntity
import com.example.retrofit_hilt_paging.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MoviesDatabase::class.java, Constants.MOVIES_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    @Provides
    @Singleton
    fun provideDao(db : MoviesDatabase) = db.moviesDoa()


    @Provides
    @Singleton
    fun provideEntity() = MoviesEntity()

}