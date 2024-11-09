//
//  CategoryListView.swift
//  iosApp
//
//  Created by Andrey de Lara on 25/03/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

struct CategoryListView: View {
    
    @ObservedObject private var viewModel: CategoryListViewModel
    
    init(category: shared.Category) {
        viewModel = CategoryListViewModel(category: category)
    }
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(spacing: 20) {
                ForEach(viewModel.state.places) { place in
                    createCategoryCell(place: place)
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding(.horizontal, 24)
        .searchable(text: $viewModel.state.searchText)
        .navigationTitle(viewModel.state.category.name)
        .navigationBarTitleDisplayMode(.large)
        .background(Color.gray2.ignoresSafeArea())
        .task { await viewModel.getPlaces() }
        .alert(item: $viewModel.state.error) {
            Alert(
                title: Text("Atenção"),
                message: Text($0.errorDescription)
            )
        }
    }
    
    private func createCategoryCell(place: shared.Place) -> some View {
        NavigationLink {
            CategoryDetailView(place: place)
                .toolbarRole(.editor)
        } label: {
            HStack(spacing: 0) {
                AsyncImage(url: .init(string: place.imageUrls.first!)) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(width: 112, height: 112, alignment: .center)
                        .clipped()
                        .cornerRadius(15)
                } placeholder: {
                    ProgressView()
                        .frame(width: 112, height: 112, alignment: .center)
                }
                .padding(10)
                
                VStack(alignment: .leading, spacing: 6) {
                    Text(place.name)
                        .font(.system(size: 15, weight: .bold))
                        .foregroundColor(.white)
                    
                    Text(place.description_)
                        .multilineTextAlignment(.leading)
                        .font(.system(size: 12, weight: .regular))
                        .foregroundColor(.white)
                    
                    Spacer()
                }
                .padding([.top, .bottom, .trailing], 10)
                
                Spacer()
            }
        }
        .frame(height: 132)
        .frame(maxWidth: .infinity)
        .overlay {
            RoundedRectangle(cornerRadius: 15)
                .stroke(lineWidth: 1)
                .foregroundColor(.gray4)
        }
    }
}
