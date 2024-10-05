//
//  CategoryDetailView.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct CategoryDetailView: View {
    
    let place: shared.Place
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(alignment: .leading, spacing: 0) {
                GeometryReader { proxy in
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 0) {
                            ForEach(place.imageUrls, id: \.self) { stringUrl in
                                AsyncImage(url: URL(string: stringUrl)!) { image in
                                    image
                                        .resizable()
                                        .aspectRatio(contentMode: .fill)
                                        .frame(
                                            width: proxy.size.width,
                                            height: proxy.size.height,
                                            alignment: .center
                                        )
                                        .clipped()
                                } placeholder: {
                                  ProgressView()
                                        .frame(
                                            width: proxy.size.width,
                                            height: proxy.size.height,
                                            alignment: .center
                                        )
                                }
                            }
                        }
                    }
                }
                .frame(height: 280)
                
                Group {
                    Text(place.name)
                        .font(.system(size: 30, weight: .bold))
                        .foregroundColor(.white)
                        .padding(.top, 24)
                    
                    Text(place.description_)
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
        .navigationTitle(place.name)
    }
}
