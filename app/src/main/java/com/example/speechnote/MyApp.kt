package com.example.speechnote

import android.app.Application
import com.example.speechnote.di.repoModule
import com.example.speechnote.di.roomModule
import com.example.speechnote.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApp)
            modules(roomModule, repoModule, viewModelModule)
        }
    }
}