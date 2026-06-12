package com.mma.formationappkmp

import android.app.Application
import com.mma.formationappkmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //On démarre Koin avec le contexte
        initKoin {
            androidContext(this@MyApp)
        }
    }
}