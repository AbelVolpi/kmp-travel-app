package com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesAndPlacesViewModel(
    private val placeRepository: PlaceRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoriesAndPlacesUiState = MutableStateFlow<UiState<CategoriesAndPlacesModel>>(UiState.Loading)
    val categoriesAndPlacesUiState: StateFlow<UiState<CategoriesAndPlacesModel>> = _categoriesAndPlacesUiState

    init {
        fetchCategoriesAndPlaces()
    }

    private fun fetchCategoriesAndPlaces() {
        viewModelScope.launch {
            val placesDeferred = async {
                var places = listOf<Place>()
                placeRepository.getRemotePlaces().collect { places = it }
                places
            }
            val categoriesDeferred = async {
                var categories = listOf<Category>()
                categoryRepository.getRemoteCategories().collect { categories = it }
                categories
            }

            val places = placesDeferred.await()
            val categories = categoriesDeferred.await()

            _categoriesAndPlacesUiState.value = UiState.Success(CategoriesAndPlacesModel(categories, places))
        }
    }
}

data class CategoriesAndPlacesModel(
    val categories: List<Category>,
    val places: List<Place>
)