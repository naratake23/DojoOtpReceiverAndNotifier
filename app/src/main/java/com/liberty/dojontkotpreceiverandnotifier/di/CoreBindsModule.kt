package com.liberty.dojontkotpreceiverandnotifier.di

import com.liberty.dojontkotpreceiverandnotifier.data.SmsCodeRepository
import com.liberty.dojontkotpreceiverandnotifier.data.SmsCodeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreBindsModule {

    @Binds
    @Singleton
    abstract fun bindSmsCodeRepository(impl: SmsCodeRepositoryImpl): SmsCodeRepository
//    =/~
//    @Provides @Singleton
//    fun provideSmsCodeRepository(dataStore: DataStore<Preferences>): SmsCodeRepository {
//        return SmsCodeRepositoryImpl(dataStore) // explicitly create it here
//    }

}