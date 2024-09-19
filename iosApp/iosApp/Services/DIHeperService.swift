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

enum ServiceError: Error {
    case genericError
    
    var description: String {
        switch self {
        case .genericError:
            return "Deu ruim aqui irmão"
        }
    }
}
