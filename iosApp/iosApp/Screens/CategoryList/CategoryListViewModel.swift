//
//  CategoryListViewModel.swift
//  iosApp
//
//  Created by Andrey de Lara on 12/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Combine

@MainActor
final class CategoryListViewModel: ObservableObject {
    
    @Published var state: CategoryListState
    
    private var cancellable: [AnyCancellable] = []
    
    let publisher = PassthroughSubject<Void, Never>()
    
    init(category: shared.Category) {
        state = CategoryListState(category: category)
        setupDebounceSearch()
    }
}

extension CategoryListViewModel {
    func getPlaces() async {
        let result = await PlaceService.shared.getPlacesBy(
            categoryId: state.category.id,
            searchText: state.searchText
        )
        
        switch result {
            case .success(let places):
                state.places = places
            case .failure(let error):
                state.error = error
        }
    }
}

extension CategoryListViewModel {
    private func setupDebounceSearch() {
        $state
            .map(\.searchText)
            .debounceSink(action: getPlaces)
            .store(in: &cancellable)
    }
}
