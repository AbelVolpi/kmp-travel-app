package com.abelvolpi.kmptravelapp.data.repository

import com.abelvolpi.kmptravelapp.data.datasource.remote.AccommodationRemoteDataSource
import com.abelvolpi.kmptravelapp.data.model.Accommodation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccommodationRepository(
    private val remoteDataSource: AccommodationRemoteDataSource
) {

    fun getAccommodations(): Flow<List<Accommodation>> = flow {
        remoteDataSource.getItems().collect { accommodations ->
            emit(accommodations)
        }
    }
}