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
}

extension AccommodationService {
    func fetchAccommodations() async {
        try? await DIHelperService.shared.accommodationRepository.fetchAccommodations()
    }
    
    func getAccommodations() async -> Result<[shared.Accommodation], ServiceError> {
        let accommodations = await DIHelperService.shared.accommodationRepository
            .getAccommodations()
            .makeAsyncIterator()
            .next()
        
        guard let accommodations, !accommodations.isEmpty else { return .failure(.genericError) }
        
        Task {
            for accommodation in accommodations {
                try? await ImageManager.shared.downloadAndSaveImage(from: accommodation.iconUrl)
            }
        }
        
        return .success(accommodations)
    }
}

extension shared.Accommodation: Identifiable { }
