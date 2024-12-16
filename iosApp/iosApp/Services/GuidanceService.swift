//
//  GuidanceService.swift
//  iosApp
//
//  Created by Andrey de Lara on 17/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

final class GuidanceService {
    
    static let shared = GuidanceService()
    
    private init() { }
}

extension GuidanceService {
    func fetchGuidelines() async {
        try? await DIHelperService.shared.guidanceRepository.fetchGuidelines()
    }
    
    func getAllGuidances() async -> Result<[shared.Guidance], ServiceError> {
        let guidances = await DIHelperService.shared.guidanceRepository
            .getAllGuidelines()
            .makeAsyncIterator()
            .next()
        
        guard let guidances, !guidances.isEmpty else { return .failure(.genericError) }
        
        return .success(guidances)
    }
    
    func getGuidanceById(id: String) async -> shared.Guidance? {
        let guidance = await DIHelperService.shared.guidanceRepository
            .getGuidanceById(id: id)
            .makeAsyncIterator()
            .next()
        
        return guidance
    }
}

extension shared.Guidance: Identifiable { }
