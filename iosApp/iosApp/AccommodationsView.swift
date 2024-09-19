//
//  AccommodationsView.swift
//  iosApp
//
//  Created by Andrey de Lara on 18/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AccommodationsView: View {
    
    let accommodations: [shared.Accommodation]
    
    var body: some View {
        VStack(spacing: 0) {
            HStack(spacing: 0) {
                Text("Acomodações")
                    .font(.system(size: 33, weight: .bold))
                Spacer()
            }
            
            Text("Fique por dentro das nossas acomodações:")
                .font(.system(size: 22, weight: .regular))
                .multilineTextAlignment(.center)
                .padding(.top, 20)
            
            VStack(spacing: 20) {
                ForEach(accommodations) { accommodation in
                    createCell(accommodation: accommodation)
                }
            }
            .padding(.top, 20)
            
            Spacer()
        }
        .padding([.horizontal, .top], 20)
    }
    
    private func createCell(accommodation: shared.Accommodation) -> some View {
        RoundedRectangle(cornerRadius: 20)
            .foregroundColor(.gray1)
            .frame(height: 82)
            .overlay {
                HStack(spacing: 0) {
                    Image("AirbnbIcon")
                    Text(accommodation.title)
                        .font(.system(size: 22, weight: .bold))
                        .multilineTextAlignment(.leading)
                        .padding(.leading, 20)
                    Spacer()
                }
                .padding(.horizontal, 20)
            }
            .onTapGesture {
                if let url = URL(string: accommodation.link), UIApplication.shared.canOpenURL(url) {
                    UIApplication.shared.open(url, options: [:], completionHandler: nil)
                }
            }
    }
}
