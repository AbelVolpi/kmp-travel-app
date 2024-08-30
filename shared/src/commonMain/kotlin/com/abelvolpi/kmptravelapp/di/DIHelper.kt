package com.abelvolpi.kmptravelapp.di

import com.abelvolpi.kmptravelapp.data.repository.CategoryRepository
import com.abelvolpi.kmptravelapp.data.repository.PlaceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DIHelper : KoinComponent {
    val categoryRepository: CategoryRepository by inject()
    val placeRepository: PlaceRepository by inject()
}
