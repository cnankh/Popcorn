package com.example.popcorn.views.playground

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popcorn.R
import com.example.popcorn.core.base.BaseFragment
import com.example.popcorn.databinding.FragmentPlaygroundBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaygroundFragment : BaseFragment<FragmentPlaygroundBinding, PlaygroundViewModel>() {


    override fun getViewModelClass() = PlaygroundViewModel::class.java
    override fun getViewBinding() = FragmentPlaygroundBinding.inflate(layoutInflater)
    
    companion object {
        fun newInstance() = PlaygroundFragment()
    }

}