package com.example.retrofit_hilt_paging.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.retrofit_hilt_paging.util.Constants.MOVIES_TABLE

@Entity(tableName = MOVIES_TABLE)
data class MoviesEntity (
    @PrimaryKey
    var id:Int =0,
    var poster : String ="",
    var title : String ="",
    var rate : String ="",
    var lang : String ="",
    var year : String =""
)