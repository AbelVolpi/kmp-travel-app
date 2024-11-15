package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.remote.AccommodationRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Accommodation
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
