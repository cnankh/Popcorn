package com.example.popcorn.views.playground.repository

import android.util.Log
import com.example.popcorn.core.base.BaseRepository
import javax.inject.Inject

class PlaygroundRepository @Inject constructor() : BaseRepository() {

    fun test(){
        Log.d("repository","test")
    }

}