//
//  AboutUsView.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AboutUsView: View {
    
    @ObservedObject private var viewModel = AboutUsViewModel()
    
    var body: some View {
        GeometryReader { proxy in
            ScrollView(.vertical, showsIndicators: false) {
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
                    
                    ForEach(viewModel.state.paragraphs, id: \.self) { paragraph in
                        Text(paragraph)
                            .foregroundColor(.white)
                            .multilineTextAlignment(.center)
                            .font(.system(size: 20, weight: .regular))
                            .padding(.top, 20)
                    }
                }
            }
        }
        .padding(.horizontal, 16)
        .background(Color.gray2)
        .task { await viewModel.getProjectDescription() }
    }
    
    var divider: some View {
        Rectangle()
            .foregroundColor(Color.gray3)
            .frame(height: 1)
    }
}
