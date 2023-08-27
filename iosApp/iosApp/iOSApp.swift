import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        Main_iosKt.inject()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
