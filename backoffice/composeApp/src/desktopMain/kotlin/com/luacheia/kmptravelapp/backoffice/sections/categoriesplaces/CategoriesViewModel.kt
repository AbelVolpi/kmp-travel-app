package com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.datasource.remote.PlaceRemoteDataSource
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val placeRepository: PlaceRepository,
    private val placeRemoteDataSource: PlaceRemoteDataSource
) : ViewModel() {

    private val _placesModel = MutableStateFlow<List<Place>?>(null)
    val placesModel: StateFlow<List<Place>?> get() = _placesModel

    fun getAllPlacesFromRemote() {
        try {
            viewModelScope.launch {
                placeRemoteDataSource.getItems().collect { places ->
                    _placesModel.value = places
                    println("PLACES: $places")
                }
            }
        } catch (e:Throwable){
            println(e)
        }

    }

}