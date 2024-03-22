package com.abelvolpi.kmptravelapp.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.Category
import com.abelvolpi.kmptravelapp.Greeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _greetingList = MutableStateFlow<List<Category>>(listOf())
    val greetingList: StateFlow<List<Category>> get() = _greetingList

    init {
        viewModelScope.launch {
            Greeting().getCategories().collect { newCategory ->
                _greetingList.update { categories -> categories + newCategory }
            }
        }
    }
}