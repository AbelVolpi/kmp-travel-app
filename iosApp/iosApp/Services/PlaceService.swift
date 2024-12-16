//
//  PlaceService.swift
//  iosApp
//
//  Created by Andrey de Lara on 28/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

final class PlaceService {
    
    static let shared = PlaceService()
    
    private init() { }
    
}

extension PlaceService {
    func fetchPlaces() async {
        try? await DIHelperService.shared.placeRepository.fetchPlaces()
    }
    
    func getPlaces(searchText: String? = nil) async -> Result<[shared.Place], ServiceError> {
        let places = await DIHelperService.shared.placeRepository
            .getAllPlaces(searchText: searchText)
            .makeAsyncIterator()
            .next()
        
        guard let places else { return .failure(.genericError) }
        
        return .success(places)
    }
    
    func getPlacesBy(categoryId: String, searchText: String? = nil) async -> Result<[shared.Place], ServiceError> {
        let places = await DIHelperService.shared.placeRepository
            .getPlacesByCategory(categoryId: categoryId, searchText: searchText)
            .makeAsyncIterator()
            .next()
        
        guard let places else { return .failure(.genericError) }
        
        return .success(places)
    }
}

extension shared.Place: Identifiable { }
