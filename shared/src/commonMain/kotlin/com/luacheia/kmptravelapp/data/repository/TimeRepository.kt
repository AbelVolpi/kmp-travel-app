package com.luacheia.kmptravelapp.data.repository

import com.luacheia.kmptravelapp.data.datasource.local.TimeLocalDataSource
import com.luacheia.kmptravelapp.data.datasource.remote.InfoRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TimeRepository(
    private val infoRemoteDataSource: InfoRemoteDataSource,
    private val timeLocalDataStore: TimeLocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getRemoteLastUpdatedTimeStamp(): Int {
        return withContext(dispatcher) {
            var result = 0
            infoRemoteDataSource.getInfo(LAST_UPDATE).collect { info ->
                result = info?.value?.toInt() ?: 0
            }
            result
        }
    }

   suspend fun getLocalLastUpdatedTimeStamp(): Int {
        return withContext(dispatcher) {
            timeLocalDataStore.getLastUpdatedTimeStamp()
        }
    }

    suspend fun saveLocalLastUpdatedTimeStamp(time: Int) {
        return withContext(dispatcher) {
            timeLocalDataStore.saveLastUpdatedTimeStamp(time)
        }
    }

    companion object {
        private const val LAST_UPDATE = "last-update"
    }
}