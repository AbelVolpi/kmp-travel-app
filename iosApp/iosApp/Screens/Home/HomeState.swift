//
//  HomeState.swift
//  iosApp
//
//  Created by Andrey de Lara on 09/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

struct HomeState {
    var aboutUsViewIsPresented = false
    var categories: [shared.Category] = []
    var places: [shared.Place] = []
    var error: ServiceError?
    var searchText = ""
}
