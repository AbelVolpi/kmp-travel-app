package com.luacheia.kmptravelapp.android.presentation.ui.explore

import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place

data class ExploreModel(
    var categories: List<Category>,
    var places: List<Place>
)
