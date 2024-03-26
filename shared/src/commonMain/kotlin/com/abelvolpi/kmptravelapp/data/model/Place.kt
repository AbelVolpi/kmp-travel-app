package com.abelvolpi.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val name: String,
    val imageUrls: List<String>,
    val description: String,
    val address: String,
    val categoryId: String,
    val price: String
)