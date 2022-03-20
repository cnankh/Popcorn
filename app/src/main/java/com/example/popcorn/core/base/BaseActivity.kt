package com.example.popcorn.core.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

/**
 * @author sina
 * v1.0
 */
abstract class BaseActivity<VBinding : ViewBinding, ViewModel : BaseViewModel> : AppCompatActivity() {

    private val TAG: String = "tag"

    protected lateinit var viewModel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>

    private var _binding: VBinding? = null
    private val binding get() = _binding!!


    protected abstract fun getViewBinding(): VBinding

    private val disposableContainer = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        primaryConfiguration()
        viewEvents()
        observer()
    }

    open fun primaryConfiguration() {}
    open fun viewEvents() {}
    open fun observer() {}

    private fun init() {
        _binding = getViewBinding()
        viewModel = ViewModelProvider(this)[getViewModelClass()]
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun dLogger(message: String, tag: String = TAG) {
        Timber.tag(tag).d(message)
    }

    fun dLogger(t: Throwable, tag: String = TAG) {
        Timber.tag(tag).d(t)
    }

    fun eLogger(message: String, tag: String = TAG) {
        Timber.tag(tag).e(message)
    }

    fun eLogger(t: Throwable, tag: String = TAG) {
        Timber.tag(tag).e(t)
    }

    fun wLogger(message: String, tag: String = TAG) {
        Timber.tag(tag).w(message)
    }

    fun wLogger(t: Throwable, tag: String = TAG) {
        Timber.tag(tag).w(t)
    }

    fun Disposable.addToContainer() = disposableContainer.add(this)

    override fun onDestroy() {

        disposableContainer.clear()
        _binding = null
        super.onDestroy()
    }

}