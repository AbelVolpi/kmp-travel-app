package com.abelvolpi.kmptravelapp.android.presentation.ui.guidance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.data.model.Guidance
import com.abelvolpi.kmptravelapp.data.repository.GuidanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GuidanceViewModel(
    private val guidanceRepository: GuidanceRepository
) : ViewModel() {

    private val _guidanceModel = MutableStateFlow<Guidance?>(null)
    val guidanceModel: StateFlow<Guidance?> get() = _guidanceModel

    fun getGuidance(guidanceId: String) {
        viewModelScope.launch {
            guidanceRepository.getGuidanceById(guidanceId).collect { guidance ->
                _guidanceModel.value = guidance
            }
        }
    }
}