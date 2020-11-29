package com.example.businessv1.frame.datasource.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryNetwork(
    @SerializedName("alias")
    @Expose
    var alias:String,
    @SerializedName("title")
    @Expose
    var title:String


)