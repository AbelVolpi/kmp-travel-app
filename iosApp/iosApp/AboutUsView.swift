//
//  AboutUsView.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AboutUsView: View {
    var body: some View {
        GeometryReader { proxy in
            VStack(spacing: 0) {
                Image.logoIcon
                    .padding(.top, 50)
                
                divider
                    .padding(.top, 60)
                
                Text("Sobre nós")
                    .foregroundColor(.white)
                    .font(.system(size: 25, weight: .bold))
                    .padding(.vertical, 10)
                
                divider
                
                Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. Lorem Ipsum is simply dummy text of been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. Lorem Ipsum is simply dummy text")
                    .foregroundColor(.white)
                    .font(.system(size: 18, weight: .regular))
                    .padding(.top, 60)
            }
        }
        .padding(.horizontal, 16)
        .background(Color.gray2)
    }
    
    var divider: some View {
        Rectangle()
            .foregroundColor(Color.gray3)
            .frame(height: 1)
    }
}

#Preview {
    AboutUsView()
}
