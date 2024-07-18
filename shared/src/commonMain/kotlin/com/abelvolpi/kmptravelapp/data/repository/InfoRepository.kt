package com.abelvolpi.kmptravelapp.data.repository

import com.abelvolpi.kmptravelapp.data.datasource.remote.InfoRemoteDataSource
import com.abelvolpi.kmptravelapp.data.model.Info
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRepository(
    private val remoteDataSource: InfoRemoteDataSource
) {

    fun getWhatsAppLink(): Flow<Info> = flow {
        remoteDataSource.getInfo("key", "whatsapp").collect { accommodations ->
            emit(accommodations)
        }
    }

    fun getProjectDescription(): Flow<Info> = flow {
        remoteDataSource.getInfo("title", "Sobre nós").collect { accommodations ->
            emit(accommodations)
        }
    }
}