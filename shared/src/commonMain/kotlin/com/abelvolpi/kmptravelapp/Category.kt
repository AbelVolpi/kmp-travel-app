package com.abelvolpi.kmptravelapp

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val iconUrl: String
)