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
    
}

extension InfoService {
    func fetchInfos() async {
        try? await DIHelperService.shared.infoRepository.fetchInfos()
    }
    
    func getWhatsAppNumber() async -> shared.Info? {
        let whatsAppNumber = await DIHelperService.shared.infoRepository
            .getWhatsAppNumber()
            .makeAsyncIterator()
            .next()
        
        return whatsAppNumber
    }
    
    func getWhatsAppMessage() async -> shared.Info? {
        let whatsAppMessage = await DIHelperService.shared.infoRepository
            .getWhatsAppMessage()
            .makeAsyncIterator()
            .next()
        
        return whatsAppMessage
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
