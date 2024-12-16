package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.local.InfoLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.InfoRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Info
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRepository(
    private val remoteDataSource: InfoRemoteDataSource,
    private val localDataSource: InfoLocalDataSource
) {
    suspend fun fetchInfos() {
        localDataSource.deleteAllInfos()
        fetchAndSaveInfo(WHATSAPP_NUMBER_KEY)
        fetchAndSaveInfo(WHATSAPP_MESSAGE_KEY)
        fetchAndSaveInfo(ABOUT_US_KEY)
    }

    private suspend fun fetchAndSaveInfo(tag: String) {
        remoteDataSource.getInfo(KEY_FIELD, tag).collect { info ->
            localDataSource.saveInfo(info ?: Info(), tag)
        }
    }

    fun getWhatsAppNumber(): Flow<Info> = flow {
        emit(localDataSource.getInfoByTag(WHATSAPP_NUMBER_KEY))
    }

    fun getWhatsAppMessage(): Flow<Info> = flow {
        emit(localDataSource.getInfoByTag(WHATSAPP_MESSAGE_KEY))
    }

    fun getProjectDescription(): Flow<Info> = flow {
        emit(localDataSource.getInfoByTag(ABOUT_US_KEY))
    }

    companion object {
        private const val KEY_FIELD = "key"
        private const val WHATSAPP_NUMBER_KEY = "whatsapp-number"
        private const val WHATSAPP_MESSAGE_KEY = "whatsapp-message"
        private const val ABOUT_US_KEY = "about-us"
    }
}
