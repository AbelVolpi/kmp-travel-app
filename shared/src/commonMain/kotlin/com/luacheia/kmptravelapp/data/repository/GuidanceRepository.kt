package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.local.GuidanceLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.GuidanceRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Guidance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GuidanceRepository(
    private val remoteDataSource: GuidanceRemoteDataSource,
    private val localDataSource: GuidanceLocalDataSource
) {
    suspend fun fetchGuidelines() {
        remoteDataSource.getItems().collect { guidelines ->
            localDataSource.deleteAllGuidelines()
            localDataSource.saveGuidelines(guidelines)
            val imageManager = ImageManager()
            imageManager.saveGuidelinesImages(guidelines)
        }
    }

    fun getAllGuidelines(): Flow<List<Guidance>> = flow {
        emit(localDataSource.getAllGuidelines())
    }

    fun getGuidanceById(id: String): Flow<Guidance> = flow {
        emit(localDataSource.getGuidanceById(id))
    }
}