//
//  AccommodationService.swift
//  iosApp
//
//  Created by Andrey de Lara on 17/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

final class AccommodationService {
    
    static let shared = AccommodationService()
    
    private init() { }
    
    func getAccommodations() async -> Result<[shared.Accommodation], ServiceError> {
        let accommodations = await DIHelperService.shared.accommodationRepository
            .getAccommodations()
            .makeAsyncIterator()
            .next()
        
        guard let accommodations, !accommodations.isEmpty else { return .failure(.genericError) }
        
        return .success(accommodations)
    }
}

extension shared.Accommodation: Identifiable { }
