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
        }
    }

    fun getAllGuidelines(): Flow<List<Guidance>> = flow {
        emit(localDataSource.getAllGuidelines())
    }

    fun getRemoteGuidances(): Flow<List<Guidance>> = remoteDataSource.getItems()

    fun getGuidanceById(id: String): Flow<Guidance?> = remoteDataSource.getItemById(id)

    fun createGuidance(title: String, subtitle: String, iconUrl: String, description: String): Flow<Boolean> =
        remoteDataSource.createGuidance(
            Guidance(id = "", title = title, subtitle = subtitle, iconUrl = iconUrl, description = description)
        )

    fun updateGuidance(guidance: Guidance): Flow<Boolean> = remoteDataSource.updateGuidance(guidance)

    fun deleteGuidance(guidanceId: String): Flow<Boolean> = remoteDataSource.deleteGuidance(guidanceId)
}