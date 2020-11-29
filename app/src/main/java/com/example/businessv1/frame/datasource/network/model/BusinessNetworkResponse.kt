package com.example.businessv1.frame.datasource.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessNetworkResponse(
    @SerializedName("businesses")
    @Expose
    var businesses:List<BusinessNetworkEntity>,
    @SerializedName("total")
    @Expose
    var total:Int
)