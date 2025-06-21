package com.luacheia.kmptravelapp.backoffice.di

import com.luacheia.kmptravelapp.backoffice.ui.sections.auth.AuthViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home.CategoriesAndPlacesViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categories.addcategory.AddCategoryViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categories.categorydetail.CategoryDetailViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.addplace.AddPlaceViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.placedetail.PlaceDetailViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.addaccommodation.AddAccommodationViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.home.AccommodationsListViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.accommodations.accommodationdetail.AccommodationDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val desktopModule = module {
    viewModel { CategoriesAndPlacesViewModel(get(),get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { AddCategoryViewModel(get()) }
    viewModel { CategoryDetailViewModel(get()) }
    viewModel { AddPlaceViewModel(get(), get()) }
    viewModel { PlaceDetailViewModel(get(), get()) }
    viewModel { AddAccommodationViewModel(get()) }
    viewModel { AccommodationsListViewModel(get()) }
    viewModel { AccommodationDetailViewModel(get()) }
}
