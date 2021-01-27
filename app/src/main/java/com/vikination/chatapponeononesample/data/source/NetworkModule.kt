package com.vikination.chatapponeononesample.data.source

import com.vikination.chatapponeononesample.data.repository.AuthRepository
import com.vikination.chatapponeononesample.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAuthRepository() :AuthRepository = AuthRepositoryImpl()
}