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
    
    func getPlaces() async -> Result<[shared.Place], ServiceError> {
        let places = await DIHelperService.shared.placeRepository
            .getAllPlaces()
            .makeAsyncIterator()
            .next()
        
        guard let places, !places.isEmpty else { return .failure(.genericError) }
        
        return .success(places)
    }
    
    func getPlacesBy(categoryId: String) async -> Result<[shared.Place], ServiceError> {
        let places = await DIHelperService.shared.placeRepository
            .getPlacesByCategory(categoryId: categoryId)
            .makeAsyncIterator()
            .next()
        
        guard let places, !places.isEmpty else { return .failure(.genericError) }
        
        return .success(places)
    }
}

extension shared.Place: Identifiable { }
