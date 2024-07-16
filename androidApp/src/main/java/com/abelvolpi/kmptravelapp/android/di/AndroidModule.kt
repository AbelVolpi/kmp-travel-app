package com.abelvolpi.kmptravelapp.android.di

import com.abelvolpi.kmptravelapp.android.app.HomeViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.category.CategoryViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.explore.ExploreViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.place.PlaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ExploreViewModel(get(), get()) }
    viewModel { PlaceViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
}