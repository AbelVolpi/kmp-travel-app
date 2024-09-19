//
//  InfoService.swift
//  iosApp
//
//  Created by Andrey de Lara on 17/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

final class InfoService {
    
    static let shared = InfoService()
    
    private init() { }
    
    func getWhatsAppLink() async -> shared.Info? {
        let whatsAppLink = await DIHelperService.shared.infoRepository
            .getWhatsAppLink()
            .makeAsyncIterator()
            .next()
        
        return whatsAppLink
    }
    
    func getProjectDescription() async -> shared.Info? {
        let projectDescription = await DIHelperService.shared.infoRepository
            .getProjectDescription()
            .makeAsyncIterator()
            .next()
        
        return projectDescription
    }
}

extension shared.Info: Identifiable { }
