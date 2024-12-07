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
    
    @StateObject private var viewModel: CategoryListViewModel
    
    init(category: shared.Category) {
        _viewModel = StateObject(wrappedValue: CategoryListViewModel(category: category))
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
        .searchable(text: $viewModel.state.searchText, prompt: "Pesquisar")
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
            PlaceDetailView(place: place)
                .toolbarRole(.editor)
        } label: {
            HStack(spacing: 0) {
                if let imageUrl = place.imageUrls.first, let url = URL(string: imageUrl) {
                    getAsyncImage(url: url, size: 112)
                } else {
                    getPlaceHolder(size: 112)
                }
                
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
    
    private func getAsyncImage(url: URL, size: CGFloat) -> some View {
        AsyncImage(url: url) { image in
            image
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: size, height: size, alignment: .center)
                .clipped()
                .cornerRadius(15)
        } placeholder: {
            ProgressView()
                .frame(width: size, height: size, alignment: .center)
        }
        .padding(10)
    }
    
    private func getPlaceHolder(size: CGFloat) -> some View {
        Image(systemName: "photo")
            .frame(width: size, height: size)
            .padding(10)
    }
}
