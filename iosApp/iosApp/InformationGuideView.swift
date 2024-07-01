//
//  InformationGuideView.swift
//  iosApp
//
//  Created by Andrey de Lara on 22/04/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct InformationGuideView: View {
    
    let title: String
    let description: AttributedString
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            HStack(spacing: 0) {
                Text(title)
                    .font(.system(size: 33, weight: .bold))
                Spacer()
            }
            Text(description)
                .multilineTextAlignment(.center)
                .padding(.top, 20)
        }
        .padding([.horizontal, .top], 20)
    }
}

#Preview {
    NavigationStack {
        InformationGuideView(title: "assdas", description: "asdasdsdas")
    }
    .preferredColorScheme(.dark)
}
