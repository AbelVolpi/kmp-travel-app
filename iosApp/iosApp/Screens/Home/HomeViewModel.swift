//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Andrey de Lara on 27/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Combine
import Foundation
import shared

@MainActor
final class HomeViewModel: ObservableObject {
    
    @Published var state = HomeState()
    
    private var cancellable: [AnyCancellable] = []
    
    init() {
        setupDebounceSearch()
    }
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
        let result = await PlaceService.shared.getPlaces(searchText: state.searchText)
        
        switch result {
            case .success(let places):
                state.places = places
            case .failure(let error):
                state.error = error
        }
    }
}

extension HomeViewModel {
    private func setupDebounceSearch() {
        $state
            .map(\.searchText)
            .debounceSink(action: getPlaces)
            .store(in: &cancellable)
    }
}
