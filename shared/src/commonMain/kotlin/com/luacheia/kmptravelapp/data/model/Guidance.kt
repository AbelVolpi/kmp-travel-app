package com.luacheia.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Guidance(
    var id: String,
    val title: String,
    val subtitle: String,
    val iconUrl: String,
    val description: String
)
