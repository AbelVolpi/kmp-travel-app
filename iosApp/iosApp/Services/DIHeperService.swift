//
//  DIHeperService.swift
//  iosApp
//
//  Created by Andrey de Lara on 27/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

final class DIHelperService {
    
    static let shared = DIHelper()
    
    private init() { }
}

enum ServiceError: Error, LocalizedError, Identifiable {
    var id: UUID { UUID() }
    
    case genericError
    
    var errorDescription: String {
        switch self {
        case .genericError:
            return "Um erro inesperado aconteceu, tente novamente mais tarde"
        }
    }
}
