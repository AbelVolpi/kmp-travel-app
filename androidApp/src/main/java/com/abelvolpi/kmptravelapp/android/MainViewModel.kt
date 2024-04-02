package com.abelvolpi.kmptravelapp.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.data.model.Category
import com.abelvolpi.kmptravelapp.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
   private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _greetingList = MutableStateFlow<List<Category>>(listOf())
    val greetingList: StateFlow<List<Category>> get() = _greetingList

    init {
        viewModelScope.launch {
            categoryRepository.fetchCategories()
            categoryRepository.getCategories().collect { newCategory ->
                _greetingList.update { _ -> newCategory }
            }
        }
    }
}