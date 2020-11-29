package com.example.businessv1.frame.datasource.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessNetworkEntity(
    @SerializedName("id")
    @Expose
    var id:String,
    @SerializedName("image_url")
    @Expose
    var image_url:String,
    @SerializedName("name")
    @Expose
    var name:String,
    @SerializedName("categories")
    @Expose
    var category:List<CategoryNetwork>,
    @SerializedName("price")
    @Expose
    var price:String,
    @SerializedName("rating")
    @Expose
    var rating:String,
    @SerializedName("review_count")
    @Expose
    var review_count :Int
)