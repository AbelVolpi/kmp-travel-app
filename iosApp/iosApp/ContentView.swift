import SwiftUI
import shared

struct ContentView: View {

    @State private var searchText = ""
    @State private var aboutUsViewIsPresented = false

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
                    createCategoryCell(label: "Cachoeiras")
                    createCategoryCell(label: "Restaurantes")
                    createCategoryCell(label: "Trilhas")
                    createCategoryCell(label: "Pontos Turísticos")
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
                    createRecommendationCell(categoryLabel: "Ponto Turísticos")
                    createRecommendationCell(categoryLabel: "Cachoeira")
                    createRecommendationCell(categoryLabel: "Trilhas")
                    createRecommendationCell(categoryLabel: "Restaurantes")
                    createRecommendationCell(categoryLabel: "Ponto Turísticos")
                    createRecommendationCell(categoryLabel: "Cachoeira")
                    createRecommendationCell(categoryLabel: "Trilhas")
                }
                .padding(.top, 10)

            }
            .padding(.horizontal, 25)
        }
        .background(Color.gray2)
        .searchable(text: $searchText)
        .navigationBarItems(trailing: infoButton)
        .sheet(isPresented: $aboutUsViewIsPresented, content: AboutUsView.init)
    }

    private var infoButton: some View {
        Image(systemName: "info.circle")
            .onTapGesture {
                aboutUsViewIsPresented.toggle()
            }
    }

    private func createCategoryCell(label: String) -> some View {
        NavigationLink {
            CategoryListView(title: label)
                .toolbarRole(.editor)
        } label: {
            VStack(spacing: 0) {
                RoundedRectangle(cornerRadius: 15)
                    .foregroundColor(.gray1)
                    .frame(width: 72, height: 72)
                    .overlay {
                        Image(systemName: "photo")
                            .resizable()
                            .scaledToFit()
                            .frame(height: 20)
                            .foregroundColor(.white)
                    }

                Text(label)
                    .font(.system(size: 10, weight: .semibold))
                    .padding(.top, 10)
                    .multilineTextAlignment(.center)
                    .foregroundColor(.white)
            }
        }
    }

    private func createRecommendationCell(categoryLabel: String) -> some View {
        NavigationLink {
            CategoryDetailView()
                .toolbarRole(.editor)
        } label: {
            RoundedRectangle(cornerRadius: 15)
                .foregroundColor(.gray1)
                .frame(width: 160, height: 160)
                .overlay(alignment: .topLeading) {
                    Text(categoryLabel)
                        .foregroundColor(.black)
                        .font(.system(size: 8, weight: .bold))
                        .padding(.horizontal, 8)
                        .background {
                            RoundedRectangle(cornerRadius: 25)
                                .foregroundColor(.white)
                        }
                        .padding(9)
                }
                .overlay {
                    Image(systemName: "photo")
                        .resizable()
                        .scaledToFit()
                        .frame(height: 50)
                        .foregroundColor(.white)
                }
        }
    }
}

extension ContentView {
    @MainActor
    class ViewModel: ObservableObject {
        @Published var greetings: [String] = []

        func startObserving() {
            Task {
                for await phrase in DIHelper().placeRepository.fetchPlaces() {
                    for place in phrase {
                        self.greetings.append(place.name)
                    }
                }

                //                try? await DIHelper().categoryRepository.fetchCategories()
                //                for await phrase in DIHelper().categoryRepository.getCategories() {
                //                    for category in phrase {
                //                        self.greetings.append(category.name)
                //                    }
                //                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            ContentView()
        }
    }
}

extension UIScreen {
    static let screenWidth = UIScreen.main.bounds.size.width
    static let screenHeight = UIScreen.main.bounds.size.height
    static let screenSize = UIScreen.main.bounds.size
}
