//
//  CategoryListViewModel.swift
//  iosApp
//
//  Created by Andrey de Lara on 12/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
final class CategoryListViewModel: ObservableObject {
    
    @Published var state: CategoryListState
    
    init(category: shared.Category) {
        state = CategoryListState(category: category)
    }
}

extension CategoryListViewModel {
    func getPlaces() async {
        let result = await PlaceService.shared.getPlacesBy(categoryId: state.category.id)
        
        switch result {
            case .success(let places):
                state.places = places
            case .failure(let error):
                state.error = error
        }
    }
}
