package com.liberty.dojontkotpreceiverandnotifier.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.work.WorkManager
import com.liberty.dojontkotpreceiverandnotifier.core.OtpBus
import com.liberty.dojontkotpreceiverandnotifier.core.OtpWorkScheduler
import com.liberty.dojontkotpreceiverandnotifier.core.PreferenceNames
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create { context.preferencesDataStoreFile(PreferenceNames.FILE) }

    @Provides
    @Singleton
    fun provideOtpBus(): OtpBus = OtpBus()

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext ctx: Context): WorkManager =
        WorkManager.getInstance(ctx)

    @Provides
    @Singleton
    fun provideOtpWorkScheduler(workManager: WorkManager): OtpWorkScheduler =
        OtpWorkScheduler(workManager)
}