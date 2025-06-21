package com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.categories.categorydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.repository.CategoryRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryDetailViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryDetailState = MutableStateFlow<UiState<Category>>(UiState.Success(Category()))
    val categoryDetailState: StateFlow<UiState<Category>> = _categoryDetailState
    private val _editCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val editCategoryState: StateFlow<UiState<Unit>> = _editCategoryState
    private val _deleteCategoryState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val deleteCategoryState: StateFlow<UiState<Unit>> = _deleteCategoryState

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
                    _deleteCategoryState.value = UiState.Success(Unit)
                } else {
                    _deleteCategoryState.value = UiState.Failure(Exception("Erro ao deletar categoria"))
                }
            }
        }
    }
}