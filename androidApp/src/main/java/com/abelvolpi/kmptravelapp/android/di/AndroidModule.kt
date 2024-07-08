package com.abelvolpi.kmptravelapp.android.di

import com.abelvolpi.kmptravelapp.android.app.HomeViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.explore.ExploreViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ExploreViewModel(get(), get()) }
}