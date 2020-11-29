package com.example.businessv1.frame.datasource.cache.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessResponseCacheEntity(
    @SerializedName("businesses")
    @Expose
    var businesses:List<BusinessCacheEntity>,
    @SerializedName("total")
    @Expose
    var total:Int
)