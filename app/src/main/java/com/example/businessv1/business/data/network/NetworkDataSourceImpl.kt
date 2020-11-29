package com.example.businessv1.business.data.network

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessResponse
import com.example.businessv1.frame.datasource.network.BusinessRetrofitService
import com.example.businessv1.frame.datasource.network.mappers.NetworkMapper
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse

class NetworkDataSourceImpl
constructor(
    private val blogRetrofitService: BusinessRetrofitService,
    private val networkMapper: NetworkMapper
): NetworkDataSource {



    override suspend fun getList(query: String, limit: Int, offset: Int): BusinessResponse {
        return networkMapper.mapFromEntity(blogRetrofitService.get(query,limit,offset))
    }

    override suspend fun getDetails(id: String): Business {
        return networkMapper.mapFromSubBusinessEntity(blogRetrofitService.getDetails(id))
    }

}