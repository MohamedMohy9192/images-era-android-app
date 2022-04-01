package com.androidera.imagesera

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * We have to create application class when use hilt
 * Hilt needs to setup classes behind the scenes for us
 * for that hilt also needs to know where our application
 * class is, it needs to have access to the application context
 * in case we need that to construct one of our dependencies
 */
@HiltAndroidApp
class UnsplashApplication : Application() {
}