package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.local.PlaceLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.PlaceRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRepository(
    private val remoteDataSource: PlaceRemoteDataSource,
    private val localDataSource: PlaceLocalDataSource
) {
    suspend fun fetchPlaces() {
        remoteDataSource.getItems().collect { places ->
            localDataSource.deleteAllPlaces()
            localDataSource.savePlaces(places)
        }
    }

    fun getAllPlaces(searchText: String? = null): Flow<List<Place>> = flow {
        emit(filterPlacesIfNeeded(localDataSource.getAllPlaces(), searchText))
    }

    fun getPlaceById(id: String): Flow<Place> = flow {
        emit(localDataSource.getPlaceById(id))
    }

    fun getPlacesByCategory(categoryId: String, searchText: String? = null): Flow<List<Place>> = flow {
        emit(filterPlacesIfNeeded(localDataSource.getPlacesByCategory(categoryId), searchText))
    }

    private fun filterPlacesIfNeeded(places: List<Place>, searchText: String?): List<Place> {
        val trimmedSearchText = searchText?.trim()

        if (trimmedSearchText.isNullOrEmpty()) {
            return places
        } else {
            val filteredPlaces = places.filter {
                it.name.lowercase().contains(trimmedSearchText.lowercase())
            }
            return filteredPlaces
        }
    }
}