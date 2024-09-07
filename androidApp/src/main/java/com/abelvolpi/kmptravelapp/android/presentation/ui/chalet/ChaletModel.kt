package com.abelvolpi.kmptravelapp.android.presentation.ui.chalet

import com.abelvolpi.kmptravelapp.data.model.Guidance
import com.abelvolpi.kmptravelapp.data.model.Info

data class ChaletModel(
    val whatsAppInfo: WhatsAppInfo,
    val hasAccommodations: Boolean,
    val guidances: List<Guidance>
)

data class WhatsAppInfo(
    val number: Info,
    val message: Info
)
