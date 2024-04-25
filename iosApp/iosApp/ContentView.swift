import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        ListView(phrases: viewModel.greetings)
            .onAppear { self.viewModel.startObserving() }
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

struct ListView: View {
    let phrases: Array<String>
    
    var body: some View {
        List(phrases, id: \.self) {
            Text($0)
        }
    }
}
