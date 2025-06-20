package com.luacheia.kmptravelapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    var id: String = "", // TODO switch to val
    val name: String = "",
    val iconUrl: String = ""
)
