package com.abelvolpi.kmptravelapp.android.di

import com.abelvolpi.kmptravelapp.android.presentation.ui.accommodation.AccommodationViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.category.CategoryViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.chalet.ChaletViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.explore.ExploreViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.guidance.GuidanceViewModel
import com.abelvolpi.kmptravelapp.android.presentation.ui.place.PlaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { ExploreViewModel(get(), get()) }
    viewModel { PlaceViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { ChaletViewModel(get(), get()) }
    viewModel { AccommodationViewModel(get()) }
    viewModel { GuidanceViewModel(get()) }
}
