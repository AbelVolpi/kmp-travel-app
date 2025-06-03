package com.luacheia.kmptravelapp.android.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.presentation.utils.UiState
import com.luacheia.kmptravelapp.data.manager.SyncManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val syncManager: SyncManager
) : ViewModel() {
    // TODO if error, show toast in UI that was not possible to update

    private val _uiState = MutableStateFlow<UiState<Any>>(UiState.Loading)
    val uiState: StateFlow<UiState<Any>> = _uiState

    init {
        fetchData()
    }

   private fun fetchData() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                syncManager.checkSynchronization()
                _uiState.value = UiState.Success(Any())
            } catch (e: Exception) {
                _uiState.value = UiState.Failure(e)
            }
        }
    }
}
