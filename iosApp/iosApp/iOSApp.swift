import SwiftUI
import composeApp

@main
struct iOSApp: App {
    
    init() {
        InitKoin().doInitDependencies()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
