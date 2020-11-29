package com.example.businessv1.di

import android.content.Context
import androidx.room.Room
import com.example.businessv1.business.data.cache.CacheDataSource
import com.example.businessv1.business.data.cache.CacheDataSourceImpl
import com.example.businessv1.frame.datasource.cache.BusinessDaoService
import com.example.businessv1.frame.datasource.cache.BusinessDaoServiceImpl
import com.example.businessv1.frame.datasource.cache.db.BusinessDao
import com.example.businessv1.frame.datasource.cache.db.BusinessDatabase
import com.example.businessv1.frame.datasource.cache.mappers.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)

object CacheModule {

    @Singleton
    @Provides
    fun provideBusinessDb(@ApplicationContext context: Context): BusinessDatabase {
        return Room
            .databaseBuilder(
                context,
                BusinessDatabase::class.java,
                BusinessDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBusinessDAO(businessDatabase: BusinessDatabase): BusinessDao {
        return businessDatabase.blogDao()
    }

    @Singleton
    @Provides
    fun provideBlogDaoService(
        blogDao: BusinessDao
    ): BusinessDaoService {
        return BusinessDaoServiceImpl(blogDao)
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(
        blogDaoService: BusinessDaoService,
        cacheMapper: CacheMapper
    ): CacheDataSource {
        return CacheDataSourceImpl(blogDaoService, cacheMapper)
    }


}