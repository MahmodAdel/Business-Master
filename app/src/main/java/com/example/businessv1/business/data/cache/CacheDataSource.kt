package com.example.businessv1.business.data.cache

import com.example.businessv1.business.domain.model.Business


interface CacheDataSource {

    suspend fun insert(business: Business): Long

    suspend fun insertList(businessList: List<Business>)

    suspend fun get(): List<Business>

    suspend fun deleteAll()
}