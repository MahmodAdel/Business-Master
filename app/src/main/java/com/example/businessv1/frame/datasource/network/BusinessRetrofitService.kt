package com.example.businessv1.frame.datasource.network

import com.example.businessv1.frame.datasource.network.model.BusinessNetworkDetails
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkEntity
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse


interface BusinessRetrofitService {
    suspend fun get(query: String, limit: Int, offset: Int): BusinessNetworkResponse
    suspend fun getDetails(id: String): BusinessNetworkDetails
}