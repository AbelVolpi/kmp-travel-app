package com.luacheia.kmptravelapp.di

import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.data.repository.InfoRepository
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DIHelper : KoinComponent {
    val categoryRepository: CategoryRepository by inject()
    val placeRepository: PlaceRepository by inject()
    val accommodationRepository: AccommodationRepository by inject()
    val guidanceRepository: GuidanceRepository by inject()
    val infoRepository: InfoRepository by inject()
}