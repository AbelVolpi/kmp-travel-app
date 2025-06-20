package com.luacheia.kmptravelapp.backoffice.sections.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.repository.UserRepository
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val authState: StateFlow<UiState<Unit>> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            userRepository.login(email, password).collect { success ->
                _authState.value = if (success) UiState.Success(Unit) else UiState.Failure(Exception("Login failed"))
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            userRepository.register(email, password).collect { success ->
                _authState.value = if (success) UiState.Success(Unit) else UiState.Failure(Exception("Registration failed"))
            }
        }
    }
}