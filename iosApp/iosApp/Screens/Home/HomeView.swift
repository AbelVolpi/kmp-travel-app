import shared
import SwiftUI

struct HomeView: View {
    
    @ObservedObject private var viewModel = HomeViewModel()
    
    let horizontalPadding: CGFloat = 25
    let gridSpacing: CGFloat = 25
    
    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    Text("Categorias")
                        .font(.system(size: 24, weight: .bold))
                        .foregroundStyle(.white)
                    Spacer()
                }
                .padding(.top, 35)
                
                HStack(alignment: .top, spacing: 16) {
                    ForEach(viewModel.state.categories) { category in
                        createCategoryCell(category: category)
                    }
                }
                .padding(.top, 10)
                
                HStack(spacing: 0) {
                    Text("Recomendações dos Anfitriões")
                        .font(.system(size: 21, weight: .bold))
                        .foregroundStyle(.white)
                    Spacer()
                }
                .padding(.top, 35)
                
                LazyVGrid(
                    columns: [GridItem(spacing: gridSpacing), GridItem(spacing: gridSpacing)],
                    spacing: gridSpacing
                ) {
                    ForEach(viewModel.state.places) { place in
                        createRecommendationCell(place: place)
                    }
                }
                .padding(.top, 10)
                
            }
            .padding(.horizontal, horizontalPadding)
        }
        .navigationBarTitleDisplayMode(.inline)
        .background(Color.gray2)
        .navigationBarItems(trailing: infoButton)
        .searchable(text: $viewModel.state.searchText, prompt: "Pesquisar")
        .sheet(isPresented: $viewModel.state.aboutUsViewIsPresented, content: AboutUsView.init)
        .alert(item: $viewModel.state.error) {
            Alert(
                title: Text("Atenção"),
                message: Text($0.errorDescription)
            )
        }
        .task {
            await viewModel.getCategories()
            await viewModel.getPlaces()
        }
        .padding(.top, -20)
    }
    
    private var infoButton: some View {
        Image(systemName: "info.circle")
            .onTapGesture {
                viewModel.state.aboutUsViewIsPresented.toggle()
            }
    }
    
    private func createCategoryCell(category: shared.Category) -> some View {
        let height: CGFloat = 35
        
        return NavigationLink {
            CategoryListView(category: category)
                .toolbarRole(.editor)
        } label: {
            VStack(spacing: 0) {
                RoundedRectangle(cornerRadius: 15)
                    .foregroundColor(.gray1)
                    .frame(width: 72, height: 72)
                    .overlay {
                        AsyncImage(url: .init(string: category.iconUrl)) { image in
                            image
                                .resizable()
                                .scaledToFit()
                                .frame(height: height)
                                .foregroundColor(.white)
                        } placeholder: {
                            ProgressView()
                                .frame(height: height)
                        }
                    }
                
                Text(category.name)
                    .font(.system(size: 10, weight: .semibold))
                    .padding(.top, 10)
                    .multilineTextAlignment(.center)
                    .foregroundColor(.white)
            }
        }
    }
    
    private func createRecommendationCell(place: shared.Place) -> some View {
        let size = (UIScreen.screenWidth - (2 * horizontalPadding) - gridSpacing) / 2
        
        return NavigationLink {
            PlaceDetailView(place: place)
                .toolbarRole(.editor)
        } label: {
            if let imageUrl = place.imageUrls.first, let url = URL(string: imageUrl) {
                getAsyncImage(url: url, size: size, place: place)
            } else {
                getPlaceHolder(size: size)
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
    
    private func getAsyncImage(url: URL, size: CGFloat, place: shared.Place) -> some View {
        AsyncImage(url: url) { image in
            image
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: size, height: size, alignment: .center)
                .clipped()
                .cornerRadius(15)
                .overlay(alignment: .topLeading) {
                    Text(place.name)
                        .foregroundColor(.black)
                        .font(.system(size: 8, weight: .bold))
                        .padding(.horizontal, 8)
                        .background {
                            RoundedRectangle(cornerRadius: 25)
                                .foregroundColor(.white)
                        }
                        .padding(9)
                }
        } placeholder: {
            ProgressView()
                .frame(width: size, height: size, alignment: .center)
        }
    }
    
    private func getPlaceHolder(size: CGFloat) -> some View {
        Image(systemName: "photo")
            .frame(width: size, height: size)
    }
}
