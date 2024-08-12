package com.abelvolpi.kmptravelapp.android.presentation.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.data.model.Place
import com.abelvolpi.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _placesModel = MutableStateFlow<List<Place>?>(null)
    val placesModel: StateFlow<List<Place>?> get() = _placesModel

    fun getPlacesByCategory(categoryId: String) {
        viewModelScope.launch {
            placeRepository.getPlacesByCategory(categoryId).collect { places ->
                _placesModel.value = places
            }
        }
    }
}