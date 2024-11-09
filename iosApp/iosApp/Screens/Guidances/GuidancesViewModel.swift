//
//  GuidancesViewModel.swift
//  iosApp
//
//  Created by Andrey de Lara on 17/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
final class GuidancesViewModel: ObservableObject {
    
    @Published var state = GuidancesState()
    
}

extension GuidancesViewModel {
    func getWhatsAppLink() async {
        let number = await InfoService.shared.getWhatsAppNumber()?.value
        let message = await InfoService.shared.getWhatsAppMessage()?.value
        
        guard let number, let message else { return }
        
        state.whatsAppLink = "https://api.whatsapp.com/send?phone=\(number)&text=\(message)"
    }
    
    func getGuidances() async {
        let response = await GuidanceService.shared.getAllGuidances()
        
        switch response {
            case .success(let guidances):
                state.guidances = guidances
            case .failure(let error):
                state.error = error
        }
    }
    
    func getAccommodations() async {
        let response = await AccommodationService.shared.getAccommodations()
        
        switch response {
            case .success(let accommodations):
                state.accommodations = accommodations
            case .failure(let error):
                state.error = error
        }
    }
}
