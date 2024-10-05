//
//  AboutUsViewModel.swift
//  iosApp
//
//  Created by Andrey de Lara on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
final class AboutUsViewModel: ObservableObject {
    
    @Published var paragraphs: [String] = []
    
}

extension AboutUsViewModel {
    func getProjectDescription() async {
        let sentense = #"\n\n"#
        
        let projectDescription = await (InfoService.shared.getProjectDescription()?.value ?? "")
            .components(separatedBy: sentense)
        
        projectDescription.forEach { paragraph in
            let paragraphCopy = paragraph.replacingOccurrences(of: sentense, with: "")
            paragraphs.append(paragraphCopy)
        }
    }
}
