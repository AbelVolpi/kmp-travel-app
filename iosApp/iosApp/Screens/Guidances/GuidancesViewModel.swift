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
    
    @Published var whatsAppLink: String?
    @Published var shouldShowAccommodations: Bool = false
    @Published var guidances: [shared.Guidance] = []
    @Published var accommodations: [shared.Accommodation] = []
    @Published var selectedGuidance: shared.Guidance?
    
}

extension GuidancesViewModel {
    func getWhatsAppLink() async {
        let number = await InfoService.shared.getWhatsAppNumber()?.value
        let message = await InfoService.shared.getWhatsAppMessage()?.value
        
        guard let number, let message else { return }
        
        whatsAppLink = "https://api.whatsapp.com/send?phone=\(number)&text=\(message)"
    }
    
    func getGuidances() async {
        let response = await GuidanceService.shared.getAllGuidances()
        
        switch response {
            case .success(let guidances):
                self.guidances = guidances
            case .failure(_):
                break
        }
    }
    
    func getAccommodations() async {
        let response = await AccommodationService.shared.getAccommodations()
        
        switch response {
            case .success(let accommodations):
                self.accommodations = accommodations
            case .failure(_):
                break
        }
    }
}
