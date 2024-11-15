package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.remote.GuidanceRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Guidance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GuidanceRepository(
    private val remoteDataSource: GuidanceRemoteDataSource
) {

    fun getAllGuidances(): Flow<List<Guidance>> = flow {
        remoteDataSource.getItems().collect { guidances ->
            emit(guidances)
        }
    }

    fun getGuidanceById(id: String): Flow<Guidance> = flow {
        remoteDataSource.getItemById(id).collect { guidance ->
            emit(guidance ?: Guidance())
        }
    }
}
