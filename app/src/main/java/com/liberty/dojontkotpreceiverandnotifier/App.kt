package com.liberty.dojontkotpreceiverandnotifier

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import androidx.hilt.work.HiltWorkerFactory
import com.liberty.dojontkotpreceiverandnotifier.core.AppVisibility
import com.liberty.dojontkotpreceiverandnotifier.core.createNotificationChannel

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        // Create the notification channel once at startup
        createNotificationChannel(this)
        // observe app foreground/background
        AppVisibility.init()
    }
}