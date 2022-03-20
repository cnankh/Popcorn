package com.example.popcorn.views.playground

import android.widget.Toast
import com.example.popcorn.core.base.BaseViewModel
import com.example.popcorn.views.playground.repository.PlaygroundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaygroundViewModel @Inject constructor(private val repository: PlaygroundRepository) :
    BaseViewModel(repository) {
    fun test() {
        Toast.makeText(context, "viewmodel", Toast.LENGTH_SHORT).show()
        repository.test()
    }
}