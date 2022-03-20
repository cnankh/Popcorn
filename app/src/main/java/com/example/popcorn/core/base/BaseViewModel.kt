package com.example.popcorn.core.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(private val repository: BaseRepository? = null) : ViewModel(),
    CoroutineScope {

    @SuppressLint("StaticFieldLeak")
    @Inject
    lateinit var context: Context

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}