package com.abelvolpi.kmptravelapp.data.repository

import com.abelvolpi.kmptravelapp.data.datasource.remote.InfoRemoteDataSource
import com.abelvolpi.kmptravelapp.data.model.Info
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InfoRepository(
    private val remoteDataSource: InfoRemoteDataSource
) {

    fun getWhatsAppNumber(): Flow<Info> = flow {
        remoteDataSource.getInfo(KEY_FIELD, WHATSAPP_NUMBER_KEY).collect { info ->
            emit(info ?: Info())
        }
    }

    fun getWhatsAppMessage(): Flow<Info> = flow {
        remoteDataSource.getInfo(KEY_FIELD, WHATSAPP_MESSAGE_KEY).collect { info ->
            emit(info ?: Info())
        }
    }

    fun getProjectDescription(): Flow<Info> = flow {
        remoteDataSource.getInfo(KEY_FIELD, ABOUT_US_KEY).collect { info ->
            emit(info ?: Info())
        }
    }

    companion object {
        private const val KEY_FIELD = "key"
        private const val WHATSAPP_NUMBER_KEY = "whatsapp-number"
        private const val WHATSAPP_MESSAGE_KEY = "whatsapp-message"
        private const val ABOUT_US_KEY = "about-us"
    }
}
