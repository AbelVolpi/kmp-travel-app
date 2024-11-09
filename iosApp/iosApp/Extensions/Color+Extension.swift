//
//  Color+Extension.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

extension Color {
    static var gray1: Color {
        Color(red: 40/255, green: 40/255, blue: 40/255)
    }
    
    static var gray2: Color {
        Color(red: 22/255, green: 22/255, blue: 22/255)
    }
    
    static var gray3: Color {
        Color(red: 65/255, green: 65/255, blue: 65/255)
    }
    
    static var gray4: Color {
        Color(red: 38/255, green: 38/255, blue: 38/255)
    }
}

extension UIColor {
    var htmlRGB: String {
        String(
            format: "#%02x%02x%02x",
            Int(rgba.red * 255),
            Int(rgba.green * 255),
            Int(rgba.blue * 255)
        )
    }
    
    var rgba: (red: CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat) {
        var r: CGFloat = 0
        var g: CGFloat = 0
        var b: CGFloat = 0
        var a: CGFloat = 0
        
        guard getRed(&r, green: &g, blue: &b, alpha: &a) else { return (0, 0, 0, 0) }
        
        return (r, g, b, a)
    }
}
