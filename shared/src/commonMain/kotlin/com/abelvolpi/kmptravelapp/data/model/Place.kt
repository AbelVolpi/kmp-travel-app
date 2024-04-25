package com.abelvolpi.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    var id: String = "",
    val name: String = "",
    val imageUrls: List<String> = listOf(""),
    val description: String = "",
    val address: String = "",
    val categoryId: String = "",
    val price: String = ""
)