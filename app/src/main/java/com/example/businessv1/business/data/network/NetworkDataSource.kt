package com.example.businessv1.business.data.network

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessDetails
import com.example.businessv1.business.domain.model.BusinessResponse
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse


interface NetworkDataSource {

     suspend fun getList(query:String, limit:Int, offset:Int): BusinessResponse
     suspend fun getDetails(id:String): BusinessDetails
}