//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Andrey de Lara on 27/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
final class HomeViewModel: ObservableObject {
    
    @Published var state = HomeState()
    
}

extension HomeViewModel {
    func getCategories() async {
        let result = await CategoryService.shared.getCategories()
        
        switch result {
            case .success(let categories):
                state.categories = categories
            case .failure(let error):
                state.error = error
        }
    }
    
    func getPlaces() async {
        let result = await PlaceService.shared.getPlaces()
        
        switch result {
            case .success(let places):
                state.places = places
            case .failure(let error):
                state.error = error
        }
    }
    
    func getCategoryName(categoryId: String) -> String {
        state.categories.first { $0.id == categoryId }?.name ?? ""
    }
}
