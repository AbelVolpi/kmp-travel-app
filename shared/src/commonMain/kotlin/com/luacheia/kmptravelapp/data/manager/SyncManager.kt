package com.luacheia.kmptravelapp.data.manager

import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.data.repository.InfoRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import com.luacheia.kmptravelapp.data.repository.TimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SyncManager(
    private val accommodationRepository: AccommodationRepository,
    private val categoryRepository: CategoryRepository,
    private val guidanceRepository: GuidanceRepository,
    private val placeRepository: PlaceRepository,
    private val infoRepository: InfoRepository,
    private val timeRepository: TimeRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun checkSynchronization() {
        withContext(dispatcher) {
            val localTimestamp = timeRepository.getLocalLastUpdatedTimeStamp()
            val remoteTimestamp = timeRepository.getRemoteLastUpdatedTimeStamp()

            if (localTimestamp < remoteTimestamp) {
                synchronizeRepositories()
                updateTimeSavedLocally(remoteTimestamp)
            }
        }
    }

    private suspend fun synchronizeRepositories() {
        accommodationRepository.fetchAccommodations()
        categoryRepository.fetchCategories()
        guidanceRepository.fetchGuidelines()
        placeRepository.fetchPlaces()
        infoRepository.fetchInfos()
    }

    private suspend fun updateTimeSavedLocally(remoteTimeStamp: Int) {
        timeRepository.saveLocalLastUpdatedTimeStamp(remoteTimeStamp)
    }

}