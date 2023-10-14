import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        InjectorKt.inject()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
