package com.example.businessv1.di

import com.example.businessv1.business.data.cache.CacheDataSource
import com.example.businessv1.business.data.network.NetworkDataSource
import com.example.businessv1.business.interactors.GetBusinessList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideGetBusiness(
        cacheDataSource: CacheDataSource,
        networkDataSource: NetworkDataSource
    ): GetBusinessList{
        return GetBusinessList(cacheDataSource, networkDataSource)
    }
}