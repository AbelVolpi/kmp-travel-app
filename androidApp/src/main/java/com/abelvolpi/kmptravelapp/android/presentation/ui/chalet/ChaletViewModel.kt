package com.abelvolpi.kmptravelapp.android.presentation.ui.chalet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abelvolpi.kmptravelapp.data.model.Guidance
import com.abelvolpi.kmptravelapp.data.model.Info
import com.abelvolpi.kmptravelapp.data.repository.GuidanceRepository
import com.abelvolpi.kmptravelapp.data.repository.InfoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChaletViewModel(
    private val infoRepository: InfoRepository,
    private val guidanceRepository: GuidanceRepository
) : ViewModel() {

    private val _chaletModel = MutableStateFlow<ChaletModel?>(null)
    val chaletModel: StateFlow<ChaletModel?> get() = _chaletModel

    init {
        getChaletScreenData()
    }

    fun getChaletScreenData() {
        viewModelScope.launch {
            val whatsAppInfoDeferred = async {
                var info = Info()
                infoRepository.getWhatsAppLink().collect { info = it }
                info
            }
            val guidancesDeferred = async {
                var guidances = listOf<Guidance>()
                guidanceRepository.getAllGuidances().collect { guidances = it }
                guidances
            }

            val info = whatsAppInfoDeferred.await()
            val guidances = guidancesDeferred.await()

            _chaletModel.value = ChaletModel(info, true, guidances)
        }
    }
}