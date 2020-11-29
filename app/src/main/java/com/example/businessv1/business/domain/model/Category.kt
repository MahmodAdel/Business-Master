package com.example.businessv1.business.domain.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @SerializedName("alias")
    @Expose
    var alias:String,
    @SerializedName("title")
    @Expose
    var title:String


) : Parcelable