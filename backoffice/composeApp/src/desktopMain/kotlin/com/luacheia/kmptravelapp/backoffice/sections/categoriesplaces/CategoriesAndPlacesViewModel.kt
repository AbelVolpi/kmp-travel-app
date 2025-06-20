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

    private val _addCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addCategoryState: StateFlow<UiState<Unit>> = _addCategoryState

    private val _categoryDetailState = MutableStateFlow<UiState<Category>>(UiState.Success(Category()))
    val categoryDetailState: StateFlow<UiState<Category>> = _categoryDetailState
    private val _editCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val editCategoryState: StateFlow<UiState<Unit>> = _editCategoryState
    private val _deleteCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val deleteCategoryState: StateFlow<UiState<Unit>> = _deleteCategoryState

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

    fun addCategory(name: String, iconUrl: String) {
        viewModelScope.launch {
            _addCategoryState.value = UiState.Loading
            categoryRepository.createCategory(Category(name = name, iconUrl = iconUrl)).collect { success ->
                if (success) {
                    fetchCategoriesAndPlaces()
                    _addCategoryState.value = UiState.Success(Unit)
                } else {
                    _addCategoryState.value = UiState.Failure(Exception("Erro ao adicionar categoria"))
                }
            }
        }
    }

    fun loadCategoryById(categoryId: String) {
        viewModelScope.launch {
            _categoryDetailState.value = UiState.Loading
            categoryRepository.getCategoryById(categoryId).collect { category ->
                if (category != null) {
                    _categoryDetailState.value = UiState.Success(category)
                } else {
                    _categoryDetailState.value = UiState.Failure(Exception("Categoria não encontrada"))
                }
            }
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            _editCategoryState.value = UiState.Loading
            categoryRepository.updateCategory(category).collect { success ->
                if (success) {
                    fetchCategoriesAndPlaces()
                    _editCategoryState.value = UiState.Success(Unit)
                } else {
                    _editCategoryState.value = UiState.Failure(Exception("Erro ao editar categoria"))
                }
            }
        }
    }

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            _deleteCategoryState.value = UiState.Loading
            categoryRepository.deleteCategory(categoryId).collect { success ->
                if (success) {
                    fetchCategoriesAndPlaces()
                    _deleteCategoryState.value = UiState.Success(Unit)
                } else {
                    _deleteCategoryState.value = UiState.Failure(Exception("Erro ao deletar categoria"))
                }
            }
        }
    }
}

data class CategoriesAndPlacesModel(
    val categories: List<Category>,
    val places: List<Place>
)