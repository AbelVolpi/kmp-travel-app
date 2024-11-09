//
//  CategoryListState.swift
//  iosApp
//
//  Created by Andrey de Lara on 09/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

struct CategoryListState {
    
    let category: shared.Category
    var searchText = ""
    var places: [shared.Place] = []
    var error: ServiceError?
    
    init(category: shared.Category) {
        self.category = category
    }
}
