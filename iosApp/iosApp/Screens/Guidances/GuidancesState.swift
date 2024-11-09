//
//  GuidancesState.swift
//  iosApp
//
//  Created by Andrey de Lara on 09/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

struct GuidancesState {
    var whatsAppLink: String?
    var shouldShowAccommodations: Bool = false
    var guidances: [shared.Guidance] = []
    var accommodations: [shared.Accommodation] = []
    var selectedGuidance: shared.Guidance?
    var error: ServiceError?
}
