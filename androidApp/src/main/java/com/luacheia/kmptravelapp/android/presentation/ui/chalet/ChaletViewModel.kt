package com.luacheia.kmptravelapp.android.presentation.ui.chalet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luacheia.kmptravelapp.data.model.Guidance
import com.luacheia.kmptravelapp.data.model.Info
import com.luacheia.kmptravelapp.data.repository.GuidanceRepository
import com.luacheia.kmptravelapp.data.repository.InfoRepository
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
            val whatsAppNumberInfoDeferred = async {
                var info = Info()
                infoRepository.getWhatsAppNumber().collect { info = it }
                info
            }
            val whatsAppMessageInfoDeferred = async {
                var info = Info()
                infoRepository.getWhatsAppMessage().collect { info = it }
                info
            }

            val guidancesDeferred = async {
                var guidances = listOf<Guidance>()
                guidanceRepository.getAllGuidances().collect { guidances = it }
                guidances
            }

            val numberInfo = whatsAppNumberInfoDeferred.await()
            val messageInfo = whatsAppMessageInfoDeferred.await()
            val guidances = guidancesDeferred.await()

            val whatsAppInfo = WhatsAppInfo(numberInfo, messageInfo)
            _chaletModel.value = ChaletModel(whatsAppInfo, true, guidances)
        }
    }
}
