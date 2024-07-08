package com.abelvolpi.kmptravelapp.android.presentation.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.data.model.Category
import com.abelvolpi.kmptravelapp.data.model.Place
import com.abelvolpi.kmptravelapp.data.repository.CategoryRepository
import com.abelvolpi.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val categoryRepository: CategoryRepository,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _exploreModel = MutableStateFlow<ExploreModel?>(null)
    val exploreModel: StateFlow<ExploreModel?> get() = _exploreModel

    init {
        getExploreScreenData()
    }

    fun getExploreScreenData() {
        viewModelScope.launch {
            val placesDeferred = async {
                var places = listOf<Place>()
                placeRepository.getAllPlaces().collect { places = it }
                places
            }
            val categoriesDeferred = async {
                var categories = listOf<Category>()
                categoryRepository.getCategories().collect { categories = it }
                categories
            }

            val places = placesDeferred.await()
            val categories = categoriesDeferred.await()

            _exploreModel.value = ExploreModel(categories, places)
        }
    }
}