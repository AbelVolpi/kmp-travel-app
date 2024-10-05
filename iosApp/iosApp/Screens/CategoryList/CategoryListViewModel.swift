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
    
    let category: shared.Category

    @Published var searchText = ""
    @Published var places: [shared.Place] = []
    
    init(category: shared.Category) {
        self.category = category
    }
}

extension CategoryListViewModel {
    func getPlaces() async {
        let result = await PlaceService.shared.getPlacesBy(categoryId: category.id)
        
        switch result {
        case .success(let places):
            self.places = places
        case .failure(let error):
            break
        }
    }
}
