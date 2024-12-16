import SwiftUI
import Firebase
import shared
import Combine

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    @StateObject
    private var setupSubject = PassthroughSubject<Void, Never>()
    
    @State
    private var zIndex: Double = 0
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                tabView
                loadingScreen
            }
            .preferredColorScheme(.dark)
            .accentColor(.white)
            .onAppear {
                UINavigationBar.appearance().titleTextAttributes = [.foregroundColor: UIColor.white]
                UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor.white]
            }
            .environmentObject(setupSubject)
        }
    }
    
    private var tabView: some View {
        TabView {
            NavigationStack {
                HomeView()
            }
            .tabItem {
                Label("", systemImage: "safari")
            }
            NavigationStack {
                GuidancesView()
            }
            .tabItem {
                Label("", systemImage: "house")
            }
        }
        .zIndex(zIndex)
    }
    
    private var loadingScreen: some View {
        ZStack {
            Color.black
            Image("Logo")
                .resizable()
                .scaledToFit()
                .frame(height: 128)
        }
        .ignoresSafeArea()
        .task {
            await PlaceService.shared.fetchPlaces()
            await CategoryService.shared.fetchCategories()
            await GuidanceService.shared.fetchGuidelines()
            await AccommodationService.shared.fetchAccommodations()
            goToHomeScreen()
        }
    }
}

extension iOSApp {
    @MainActor
    private func goToHomeScreen() {
        zIndex = 1
        setupSubject.send()
        setupSubject.send(completion: .finished)
    }
}
