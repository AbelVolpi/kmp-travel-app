package com.abelvolpi.kmptravelapp.data.repository

import com.abelvolpi.kmptravelapp.data.datasource.local.PlaceLocalDataSource
import com.abelvolpi.kmptravelapp.data.datasource.remote.PlaceRemoteDataSource
import com.abelvolpi.kmptravelapp.data.model.Category
import com.abelvolpi.kmptravelapp.data.model.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaceRepository(
    private val remoteDataSource: PlaceRemoteDataSource,
    private val localDataSource: PlaceLocalDataSource
) {
    suspend fun fetchPlaces() {
        remoteDataSource.getPlaces().collect { places ->
            localDataSource.deleteAllPlaces()
            localDataSource.savePlaces(places)
        }
    }

    fun getAllPlaces(): Flow<List<Place>> = flow {
        emit(localDataSource.getAllPlaces())
    }

    fun getPlaceById(id: String): Flow<Place> = flow {
        emit(localDataSource.getPlaceById(id))
    }

    fun getPlacesByCategory(categoryId: String): Flow<List<Place>> = flow {
        emit(localDataSource.getPlacesByCategory(categoryId))
    }
}