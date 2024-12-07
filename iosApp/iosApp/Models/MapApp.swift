//
//  MapApp.swift
//  iosApp
//
//  Created by Andrey de Lara on 07/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import CoreLocation
import Foundation
import UIKit

enum MapApp: CaseIterable {
    case appleMaps
    case googleMaps
    case waze
    
    var title: String {
        switch self {
            case .appleMaps:
                return "Apple Maps"
            case .googleMaps:
                return "Google Maps"
            case .waze:
                return "Waze"
        }
    }
    
    private var appPath: String {
        switch self {
            case .appleMaps:
                return "http://maps.apple.com"
            case .googleMaps:
                return "comgooglemaps://"
            case .waze:
                return "waze://"
        }
    }
    
    var isAvailable: Bool {
        switch self {
            case .appleMaps:
                return true
            case .googleMaps, .waze:
                if let url = URL(string: appPath) {
                    return UIApplication.shared.canOpenURL(url)
                } else {
                    return false
                }
        }
    }
    
    func getURL(from destinationAddress: String) -> URL? {
        let encodedAddress = destinationAddress.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? ""
        let url: URL?
        
        switch self {
            case .appleMaps:
                url = URL(string: "http://maps.apple.com/?daddr=\(encodedAddress)")
            case .googleMaps:
                url = URL(string: "comgooglemaps://?daddr=\(encodedAddress)&directionsmode=driving")
            case .waze:
                url = URL(string: "https://waze.com/ul?q=\(encodedAddress)&navigate=yes")
        }
        
        return url
    }
}
