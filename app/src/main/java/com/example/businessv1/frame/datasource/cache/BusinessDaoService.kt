package com.example.businessv1.frame.datasource.cache

import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheFavorite


interface BusinessDaoService {

    suspend fun insert(businessEntity: BusinessCacheEntity): Long

    suspend fun get(): List<BusinessCacheEntity>
    suspend fun getFav(): List<BusinessCacheFavorite>

    suspend fun deleteAll()

    suspend fun insertFav(businessCacheFavorite: BusinessCacheFavorite): Long

    suspend fun deleteBusinessFav(businessId:String)

}