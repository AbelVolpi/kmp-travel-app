package com.luacheia.kmptravelapp.backoffice.di

import com.luacheia.kmptravelapp.backoffice.ui.sections.auth.AuthViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home.CategoriesAndPlacesViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categories.addcategory.AddCategoryViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categories.categorydetail.CategoryDetailViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.addplace.AddPlaceViewModel
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.places.placedetail.PlaceDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val desktopModule = module {
    viewModel { CategoriesAndPlacesViewModel(get(),get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { AddCategoryViewModel(get()) }
    viewModel { CategoryDetailViewModel(get()) }
    viewModel { AddPlaceViewModel(get(), get()) }
    viewModel { PlaceDetailViewModel(get(), get()) }
}
