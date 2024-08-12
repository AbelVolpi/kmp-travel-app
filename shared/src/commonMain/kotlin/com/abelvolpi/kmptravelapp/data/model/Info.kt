package com.abelvolpi.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    var id: String = "",
    val key: String = "",
    val value: String = ""
)
