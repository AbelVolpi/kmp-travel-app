package com.abelvolpi.kmptravelapp.android.di

import com.abelvolpi.kmptravelapp.android.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { MainViewModel(get()) }
}