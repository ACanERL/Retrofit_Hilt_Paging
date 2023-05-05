package com.example.retrofit_hilt_paging.util

import android.view.View

fun View.showInvisible(isShown: Boolean) {
    if (isShown) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}