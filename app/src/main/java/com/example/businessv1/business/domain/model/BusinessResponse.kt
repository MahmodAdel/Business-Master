package com.example.businessv1.business.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessResponse(
    @SerializedName("businesses")
    @Expose
    var businesses:List<Business>,
    @SerializedName("total")
    @Expose
    var total:Int
)