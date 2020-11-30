package com.example.businessv1.business.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessDetails(
    @SerializedName("id")
    @Expose
    var id:String,

    @SerializedName("alias")
    @Expose
    var alias:String,

    @SerializedName("name")
    @Expose
    var name:String,

    @SerializedName("image_url")
    @Expose
    var image_url:String,

    @SerializedName("phone")
    @Expose
    var phone:String,

    @SerializedName("review_count")
    @Expose
    var review_count:Int,

    @SerializedName("categories")
    @Expose
    var categories:List<Category>,

    @SerializedName("rating")
    @Expose
    var rating:Double,

    @SerializedName("Location")
    @Expose
    var location:Location


)