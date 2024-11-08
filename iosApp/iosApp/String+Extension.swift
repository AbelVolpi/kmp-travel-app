//
//  String+Extension.swift
//  iosApp
//
//  Created by Andrey de Lara on 04/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UIKit

extension String {
    func htmlAttributedString(
        fontSize: CGFloat = 20,
        normalFontColor: UIColor = .white,
        highlightedFontColor: UIColor = .white
    ) -> AttributedString {
        let format =
        """
        <style>
        body { font-family: '%@'; font-size:%fpx; color: %@; }
        b { color: %@; }
        </style>
        """
        
        let string = appending(
            String(
                format: format,
                UIFont.systemFont(ofSize: fontSize).familyName,
                fontSize,
                normalFontColor.htmlRGB,
                highlightedFontColor.htmlRGB
            )
        )
        
        guard let data = string.data(using: String.Encoding.utf16, allowLossyConversion: false),
              let html = try? NSMutableAttributedString(
                data: data,
                options: [NSAttributedString.DocumentReadingOptionKey.documentType: NSAttributedString.DocumentType.html],
                documentAttributes: nil
              )
        else { return AttributedString("") }
        
        return AttributedString(html)
    }
}

