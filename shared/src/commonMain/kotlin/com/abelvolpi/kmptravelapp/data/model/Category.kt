package com.abelvolpi.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    var id: String = "",
    val name: String = "",
    val iconUrl: String = ""
)
