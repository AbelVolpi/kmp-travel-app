import shared
import SwiftUI
import UIKit

struct HomeView: View {
    
    @ObservedObject private var viewModel = HomeViewModel()
    
    let cellWidth = (UIScreen.screenWidth - (2 * 25) - 15) / 2
    
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
                    ForEach(viewModel.categories) { category in
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
                
                LazyVGrid(columns: [GridItem(spacing: 15), GridItem(spacing: 15)], spacing: 15) {
                    ForEach(viewModel.places) { place in
                        createRecommendationCell(place: place)
                    }
                }
                .padding(.top, 10)
                
            }
            .padding(.horizontal, 25)
        }
        .background(Color.gray2)
        .navigationBarItems(trailing: infoButton)
        .sheet(isPresented: $viewModel.aboutUsViewIsPresented, content: AboutUsView.init)
        .task {
            await viewModel.getCategories()
            await viewModel.getPlaces()
        }
    }
    
    private var infoButton: some View {
        Image(systemName: "info.circle")
            .onTapGesture {
                viewModel.aboutUsViewIsPresented.toggle()
            }
    }
    
    private func createCategoryCell(category: shared.Category) -> some View {
        NavigationLink {
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
                                .frame(height: 35)
                                .foregroundColor(.white)
                        } placeholder: {
                            ProgressView()
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
        NavigationLink {
            CategoryDetailView(place: place)
                .toolbarRole(.editor)
        } label: {
            AsyncImage(url: URL(string: place.imageUrls.first!)!) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: cellWidth, height: cellWidth, alignment: .center)
                    .clipped()
                    .clipShape(RoundedRectangle(cornerRadius: 15))
                    .overlay(alignment: .topLeading) {
                        Text(viewModel.getCategoryName(categoryId: place.categoryId))
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
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            HomeView()
        }
    }
}

extension UIScreen {
    static let screenWidth = UIScreen.main.bounds.size.width
    static let screenHeight = UIScreen.main.bounds.size.height
    static let screenSize = UIScreen.main.bounds.size
}

//private func createRecommendationCell(place: shared.Place) -> some View {
//    NavigationLink {
//        CategoryDetailView(place: place)
//            .toolbarRole(.editor)
//    } label: {
//        AsyncImage(url: URL(string: place.imageUrls.first!)!) { image in
//            image
//                .resizable()
//                .scaledToFit()
//                .frame(maxHeight: .infinity)
//                .clipShape(RoundedRectangle(cornerRadius: 15))
//                .overlay(alignment: .topLeading) {
//                    Text(viewModel.getCategoryName(categoryId: place.categoryId))
//                        .foregroundColor(.black)
//                        .font(.system(size: 8, weight: .bold))
//                        .padding(.horizontal, 8)
//                        .background {
//                            RoundedRectangle(cornerRadius: 25)
//                                .foregroundColor(.white)
//                        }
//                        .padding(9)
//                }
//        } placeholder: {
//            ProgressView()
//        }
//    }
////        .frame(maxHeight: .infinity)
//}
