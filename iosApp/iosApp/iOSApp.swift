import SwiftUI

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            TabView {
                NavigationStack {
                    ContentView()
                }
                .tabItem {
                    Label("", systemImage: "safari")
                }
                NavigationStack {
                    InformationsView()
                }
                .tabItem {
                    Label("", systemImage: "house")
                }
            }
            .preferredColorScheme(.dark)
            .accentColor(.white)
            .onAppear {
                UINavigationBar.appearance().titleTextAttributes = [.foregroundColor: UIColor.white]
                UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor.white]
            }
        }
	}
}
