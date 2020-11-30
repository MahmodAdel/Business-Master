package com.example.businessv1.business.data.cache

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessFavorite


interface CacheDataSource {

    suspend fun insert(business: Business): Long

    suspend fun insertList(businessList: List<Business>)

    suspend fun get(): List<Business>
    suspend fun deleteAll()
    suspend fun deleteBusinessFav(businessId:String)
    suspend fun insertFavorite(business: Business)

    suspend fun getFav():List<Business>
}