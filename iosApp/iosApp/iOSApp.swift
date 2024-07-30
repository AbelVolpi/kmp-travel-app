import SwiftUI
import Firebase
import shared

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
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
