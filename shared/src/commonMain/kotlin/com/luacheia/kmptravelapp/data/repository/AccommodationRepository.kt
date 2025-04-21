package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.local.AccommodationLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.AccommodationRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Accommodation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccommodationRepository(
    private val remoteDataSource: AccommodationRemoteDataSource,
    private val localDataSource: AccommodationLocalDataSource
) {
    suspend fun fetchAccommodations() {
        remoteDataSource.getItems().collect { accommodations ->
            localDataSource.deleteAllAccommodations()
            localDataSource.saveAccommodations(accommodations)
            val imageManager = ImageManager()
            imageManager.saveAccommodationsImages(accommodations)
        }
    }

    fun getAccommodations(): Flow<List<Accommodation>> = flow {
        emit(localDataSource.getAllAccommodations())
    }
}
