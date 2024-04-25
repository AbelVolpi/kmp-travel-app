import SwiftUI
import Firebase
import shared

@main
struct iOSApp: App {
    
    init(){
        FirebaseApp.configure()
        InitDIHelperKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: ContentView.ViewModel())
        }
    }
}