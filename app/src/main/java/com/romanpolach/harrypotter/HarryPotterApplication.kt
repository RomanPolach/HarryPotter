package com.romanpolach.harrypotter

import android.app.Application
import com.romanpolach.harrypotter.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Application class for initializing Koin dependency injection.
 */
class HarryPotterApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            // Use Android logger - Level.INFO by default
            androidLogger()
            androidContext(this@HarryPotterApplication)
            modules(appModules)
        }
    }
}
