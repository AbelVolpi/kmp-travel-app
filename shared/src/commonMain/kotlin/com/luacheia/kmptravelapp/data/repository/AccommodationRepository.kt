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
        }
    }

    fun getAccommodations(): Flow<List<Accommodation>> = flow {
        emit(localDataSource.getAllAccommodations())
    }

    fun getRemoteAccommodations(): Flow<List<Accommodation>> = remoteDataSource.getItems()

    fun getAccommodationById(id: String): Flow<Accommodation?> = remoteDataSource.getItemById(id)

    fun createAccommodation(title: String, iconUrl: String, link: String): Flow<Boolean> =
        remoteDataSource.createAccommodation(
            Accommodation(id = "", title = title, iconUrl = iconUrl, link = link)
        )

    fun updateAccommodation(accommodation: Accommodation): Flow<Boolean> =
        remoteDataSource.updateAccommodation(accommodation)

    fun deleteAccommodation(accommodationId: String): Flow<Boolean> =
        remoteDataSource.deleteAccommodation(accommodationId)
}
