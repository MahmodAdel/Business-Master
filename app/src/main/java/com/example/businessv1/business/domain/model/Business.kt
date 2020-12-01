package com.example.businessv1.business.domain.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Business(
    @SerializedName("id")
    @Expose
    var id:String,
    @SerializedName("image_url")
    @Expose
    var image_url:String,
    @SerializedName("name")
    @Expose
    var name:String,
    @SerializedName("category")
    @Expose
    var category: List<Category>,
    @SerializedName("price")
    @Expose
    var price:String,
    @SerializedName("rating")
    @Expose
    var rating:String,
    @SerializedName("review_count")
    @Expose
    var review_count:Int
) : Parcelable