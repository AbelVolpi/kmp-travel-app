package com.abelvolpi.kmptravelapp.android.presentation.ui.explore

import com.abelvolpi.kmptravelapp.data.model.Category
import com.abelvolpi.kmptravelapp.data.model.Place

data class ExploreModel(
    var categories: List<Category>,
    var places: List<Place>,
)