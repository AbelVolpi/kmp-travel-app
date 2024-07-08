package com.abelvolpi.kmptravelapp.android.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.data.model.Place
import com.abelvolpi.kmptravelapp.data.repository.CategoryRepository
import com.abelvolpi.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _greetingList = MutableStateFlow<List<Place>>(listOf())
    val greetingList: StateFlow<List<Place>> get() = _greetingList

    init {
        viewModelScope.launch {

//            categoryRepository.fetchCategories()
//            categoryRepository.getCategories().collect { newCategory ->
//                _greetingList.update { _ -> newCategory }
//            }

//            placeRepository.fetchPlaces()
//            placeRepository.getAllPlaces().collect { place ->
//                _greetingList.update { _ -> place }
//            }
        }
    }
}