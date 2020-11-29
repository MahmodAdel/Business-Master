package com.example.businessv1.frame.datasource.cache

import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity


interface BusinessDaoService {

    suspend fun insert(businessEntity: BusinessCacheEntity): Long

    suspend fun get(): List<BusinessCacheEntity>

    suspend fun deleteAll()

}