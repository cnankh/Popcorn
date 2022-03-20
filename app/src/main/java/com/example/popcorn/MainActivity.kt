package com.example.popcorn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.popcorn.core.base.BaseActivity
import com.example.popcorn.databinding.ActivityMainBinding
import com.example.popcorn.databinding.FragmentPlaygroundBinding
import com.example.popcorn.views.playground.PlaygroundViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override fun getViewModelClass() = MainActivityViewModel::class.java
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun primaryConfiguration() {
        super.primaryConfiguration()
        makeToast("activity")
    }

}