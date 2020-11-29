package com.example.businessv1.business.data.cache

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.frame.datasource.cache.BusinessDaoService
import com.example.businessv1.frame.datasource.cache.mappers.CacheMapper


class CacheDataSourceImpl
constructor(
    private val businessDaoService: BusinessDaoService,
    private val cacheMapper: CacheMapper
): CacheDataSource{

    override suspend fun insert(business: Business): Long {
        return businessDaoService.insert(cacheMapper.mapToSubEntity(business))
    }

    override suspend fun insertList(businesslist: List<Business>){
        for(business in businesslist) {
            businessDaoService.insert(cacheMapper.mapToSubEntity(business))
        }
    }

    override suspend fun get(): List<Business> {
        return cacheMapper.mapFromSubEntityListBusiness(businessDaoService.get())
    }

    override suspend fun deleteAll() {
        businessDaoService.deleteAll()
    }

}
