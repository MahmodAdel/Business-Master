package com.example.businessv1.frame.datasource.network.retrofit


import com.example.businessv1.frame.datasource.network.model.BusinessNetworkDetails
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkEntity
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BusinessInterface{
    @GET("search")
    suspend fun getBusiness(
        @Query("location") location:String,
        @Query("limit") limit:Int,
        @Query("offset") offset:Int): BusinessNetworkResponse

    @GET("{id}")
    suspend fun getBusinessDetails( @Path("id")id: String): BusinessNetworkDetails
}