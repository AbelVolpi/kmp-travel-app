package com.luacheia.kmptravelapp.android.presentation.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _placeModel = MutableStateFlow<Place?>(null)
    val placeModel: StateFlow<Place?> get() = _placeModel

    fun getPlace(placeId: String) {
        viewModelScope.launch {
            placeRepository.getPlaceById(placeId).collect { place ->
                _placeModel.value = place
            }
        }
    }
}
