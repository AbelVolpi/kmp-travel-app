package com.luacheia.kmptravelapp.android.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.android.presentation.utils.UiState
import com.luacheia.kmptravelapp.data.repository.AccommodationRepository
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.data.repository.InfoRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val placeRepository: PlaceRepository,
    private val categoryRepository: CategoryRepository,
    private val guidanceRepository: GuidanceRepository,
    private val accommodationRepository: AccommodationRepository,
    private val infoRepository: InfoRepository,
//    TODO implement SyncManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
    val uiState: StateFlow<UiState<Any>> = _uiState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val placesDeferred = async { placeRepository.fetchPlaces() }
                    val categoriesDeferred = async { categoryRepository.fetchCategories() }
                    val guidelinesDeferred = async { guidanceRepository.fetchGuidelines() }
                    val accommodationsDeferred = async { accommodationRepository.fetchAccommodations() }
                    val infosDeferred = async { infoRepository.fetchInfos() }

                    placesDeferred.await()
                    categoriesDeferred.await()
                    guidelinesDeferred.await()
                    accommodationsDeferred.await()
                    infosDeferred.await()

                    _uiState.value = UiState.Success(Any())
                } catch (e: Exception) {
                    _uiState.value = UiState.Failure(e)
                }
            }
        }
    }
}