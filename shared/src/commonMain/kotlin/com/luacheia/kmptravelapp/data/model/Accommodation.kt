package com.luacheia.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Accommodation(
    var id: String,
    val title: String,
    val iconUrl: String,
    val link: String
)
