package com.example.businessv1.frame.datasource.network

import com.example.businessv1.frame.datasource.network.model.BusinessNetworkDetails
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkEntity
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse
import com.example.businessv1.frame.datasource.network.retrofit.BusinessInterface


class BusinessRetrofitServiceImpl
constructor(
    private val businessInterface: BusinessInterface
) : BusinessRetrofitService{
    override suspend fun get(query: String, limit: Int, offset: Int): BusinessNetworkResponse {
        return businessInterface.getBusiness(query,limit,offset)
    }

    override suspend fun getDetails(id: String): BusinessNetworkDetails {
        return businessInterface.getBusinessDetails(id)
    }

}
