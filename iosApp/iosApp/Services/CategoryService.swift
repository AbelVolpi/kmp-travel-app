//
//  CategoryService.swift
//  iosApp
//
//  Created by Andrey de Lara on 28/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

final class CategoryService {
    
    static let shared = CategoryService()
    
    private init() { }
    
}

extension CategoryService {
    static func fetchCategories() async {
        try? await DIHelperService.shared.categoryRepository.fetchCategories()
    }
    
    func getCategories() async -> Result<[shared.Category], ServiceError> {
        let categories = await DIHelperService.shared.categoryRepository
            .getCategories()
            .makeAsyncIterator()
            .next()
        
        guard let categories, !categories.isEmpty else { return .failure(.genericError) }
        
        return .success(categories)
    }
}

extension shared.Category: Identifiable { }
