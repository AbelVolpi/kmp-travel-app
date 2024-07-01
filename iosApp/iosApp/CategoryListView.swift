//
//  CategoryListView.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CategoryListView: View {
    
    @State private var searchText = ""
    
    let title: String
    
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(spacing: 20) {
                createCategoryCell(title: "Morro da Igreja")
                createCategoryCell(title: "Morro da Igreja")
                createCategoryCell(title: "Morro da Igreja")
                createCategoryCell(title: "Morro da Igreja")
                createCategoryCell(title: "Morro da Igreja")
                createCategoryCell(title: "Morro da Igreja")
                createCategoryCell(title: "Morro da Igreja")
            }
        }
        .padding(.horizontal, 24)
        .searchable(text: $searchText)
        .navigationTitle(title)
        .navigationBarTitleDisplayMode(.large)
        .background(Color.gray2.ignoresSafeArea())
    }
    
    private func createCategoryCell(title: String) -> some View {
        NavigationLink {
            CategoryDetailView()
                .toolbarRole(.editor)
        } label: {
            HStack(spacing: 0) {
                Image(systemName: "photo")
                    .resizable()
                    .foregroundColor(.white)
                    .scaledToFit()
                    .frame(width: 112)
                    .padding(10)
                
                VStack(alignment: .leading, spacing: 6) {
                    Text(title)
                        .font(.system(size: 13, weight: .bold))
                        .foregroundColor(.white)
                    
                    Text("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s  the 1500 simply dummy text of the simply dummy text of the simply dummy text of the simply dummy text of the ever since the 1500s  the 1500 simply dummy text of the simply dummy text of the simply dummy text of the simply")
                        .multilineTextAlignment(.leading)
                        .font(.system(size: 10, weight: .regular))
                        .foregroundColor(.white)
                }
                .padding([.top, .bottom, .trailing], 10)
            }
        }
        .frame(height: 132)
        .overlay {
            RoundedRectangle(cornerRadius: 15)
                .stroke(lineWidth: 1)
                .foregroundColor(.gray4)
        }
    }
}

#Preview {
    NavigationStack {
        CategoryListView(title: "Ponto Turistico")
    }
}
