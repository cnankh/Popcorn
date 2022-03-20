package com.example.popcorn.core.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context


    companion object {

        @Provides
        fun provideCompositeDisposable(): CompositeDisposable {
            return CompositeDisposable()
        }
    }

}