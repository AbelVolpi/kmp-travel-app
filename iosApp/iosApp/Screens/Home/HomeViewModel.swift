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
    
    @Published var aboutUsViewIsPresented = false
    @Published var categories: [shared.Category] = []
    @Published var places: [shared.Place] = []
    @Published var errorDescription: String?
    
}

extension HomeViewModel {
    func getCategories() async {
        let result = await CategoryService.shared.getCategories()
        
        switch result {
        case .success(let categories):
            self.categories = categories
        case .failure(let error):
            errorDescription = error.description
        }
    }
    
    func getPlaces() async {
        let result = await PlaceService.shared.getPlaces()
        
        switch result {
        case .success(let places):
            self.places = places
        case .failure(let error):
            errorDescription = error.description
        }
    }
    
    func getCategoryName(categoryId: String) -> String {
        categories.first { $0.id == categoryId }?.name ?? ""
    }
}
