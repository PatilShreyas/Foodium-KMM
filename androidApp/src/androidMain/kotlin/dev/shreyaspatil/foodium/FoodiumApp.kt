package dev.shreyaspatil.foodium

import android.app.Application
import di.component.AppComponent

class FoodiumApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.Injector.inject(this)
    }
}