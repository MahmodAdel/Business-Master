package com.example.businessv1.frame.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "businessfav")
data class BusinessCacheFavorite(
    @PrimaryKey
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
    var category:List<CategoryCacheEntity>,
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