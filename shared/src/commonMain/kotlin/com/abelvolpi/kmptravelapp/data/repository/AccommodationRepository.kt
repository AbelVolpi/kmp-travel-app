package com.abelvolpi.kmptravelapp.data.repository

import com.abelvolpi.kmptravelapp.data.datasource.remote.AccommodationRemoteDataSource
import com.abelvolpi.kmptravelapp.data.model.Accommodation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccommodationRepository(
    private val remoteDataSource: AccommodationRemoteDataSource
) {

    fun getCategories(): Flow<List<Accommodation>> = flow {
        remoteDataSource.getAccommodations().collect { accommodations ->
            emit(accommodations)
        }
    }
}