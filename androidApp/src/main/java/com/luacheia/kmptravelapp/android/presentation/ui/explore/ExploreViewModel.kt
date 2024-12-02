package com.luacheia.kmptravelapp.android.presentation.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce

class ExploreViewModel(
    private val categoryRepository: CategoryRepository,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _exploreModel = MutableStateFlow<ExploreModel?>(null)
    val exploreModel: StateFlow<ExploreModel?> get() = _exploreModel

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    init {
        getExploreScreenData()
        setupDebounceSearch()
    }

    private fun getExploreScreenData() {
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

    fun searchPlaces(searchText: String? = null) {
        viewModelScope.launch {
            placeRepository.getAllPlaces(searchText).collect { updatedPlaces ->
                _exploreModel.value = _exploreModel.value?.copy(places = updatedPlaces)
            }
        }
    }

    fun onSearchQueryChange(searchText: String) {
        _searchText.value = searchText
    }

    private fun setupDebounceSearch() {
        viewModelScope.launch {
            _searchText
                .debounce(400)
                .filter { it.trim().isNotEmpty() }
                .distinctUntilChanged()
                .collect {
                    print("Passou")
                    searchPlaces(it)
                }
        }
    }
}
