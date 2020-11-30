package com.example.businessv1.frame.datasource.cache

import com.example.businessv1.frame.datasource.cache.BusinessDaoService
import com.example.businessv1.frame.datasource.cache.db.BusinessDao
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheFavorite

class BusinessDaoServiceImpl
constructor(
    private val businessDao: BusinessDao
): BusinessDaoService {

    override suspend fun insert(businessEntity: BusinessCacheEntity): Long {
        return businessDao.insert(businessEntity)
    }

    override suspend fun get(): List<BusinessCacheEntity> {
        return businessDao.get()
    }

    override suspend fun getFav(): List<BusinessCacheFavorite> {
        return businessDao.getFav()
    }

    override suspend fun deleteAll() {
        return businessDao.deleteAll()
    }

    override suspend fun insertFav(businessCacheFavorite: BusinessCacheFavorite):Long {
        return businessDao.insertFav(businessCacheFavorite)
    }

    override suspend fun deleteBusinessFav(businessId: String) {
        businessDao.deleteBusiness(businessId)
    }
}