package com.luacheia.kmptravelapp.android.di

import com.luacheia.kmptravelapp.android.presentation.ui.accommodation.AccommodationViewModel
import com.luacheia.kmptravelapp.android.presentation.ui.category.CategoryViewModel
import com.luacheia.kmptravelapp.android.presentation.ui.chalet.ChaletViewModel
import com.luacheia.kmptravelapp.android.presentation.ui.explore.ExploreViewModel
import com.luacheia.kmptravelapp.android.presentation.ui.guidance.GuidanceViewModel
import com.luacheia.kmptravelapp.android.presentation.ui.home.HomeViewModel
import com.luacheia.kmptravelapp.android.presentation.ui.place.PlaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { ExploreViewModel(get(), get()) }
    viewModel { PlaceViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { ChaletViewModel(get(), get()) }
    viewModel { AccommodationViewModel(get()) }
    viewModel { GuidanceViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
}
