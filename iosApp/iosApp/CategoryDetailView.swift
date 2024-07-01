//
//  CategoryDetailView.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CategoryDetailView: View {
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(alignment: .leading, spacing: 0) {
                GeometryReader { proxy in
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 0) {
                            ForEach(0..<5) { _ in
                                Image(systemName: "photo")
                                    .resizable()
                                    .scaledToFit()
                                    .foregroundColor(.white)
                                    .frame(width: proxy.size.width)
                            }
                        }
                    }
                }
                .frame(height: 280)
                
                Group {
                    Text("Morro da Igreja")
                        .font(.system(size: 30, weight: .bold))
                        .foregroundColor(.white)
                        .padding(.top, 24)
                    
                    Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since.")
                        .font(.system(size: 18, weight: .regular))
                        .foregroundColor(.white)
                        .padding(.top, 15)
                    
                    Button {
                        
                    } label: {
                        HStack(spacing: 0) {
                            Spacer()
                            HStack(spacing: 15) {
                                Image(systemName: "location.fill")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(height: 36)
                                    .foregroundColor(.white)
                                Text("Traçar rota")
                                    .foregroundColor(.white)
                                    .font(.system(size: 22, weight: .bold))
                            }
                            .padding(15)
                            .overlay {
                                RoundedRectangle(cornerRadius: 15)
                                    .stroke()
                                    .foregroundColor(.white)
                            }
                            Spacer()
                        }
                    }
                    .padding(.top, 25)
                    .frame(alignment: .center)

                }
                .padding(.horizontal, 24)
            }
        }
        .background(Color.gray2.ignoresSafeArea())
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    NavigationStack {
        CategoryDetailView()
    }
}
