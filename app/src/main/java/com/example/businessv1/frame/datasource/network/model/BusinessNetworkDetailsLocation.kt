package com.example.businessv1.frame.datasource.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessNetworkDetailsLocation(
    @SerializedName("address1")
    @Expose
    var address1:String="",


    @SerializedName("address2")
    @Expose
    var address2:String="",


    @SerializedName("address3")
    @Expose
    var address3:String="",


    @SerializedName("city")
    @Expose
    var city:String="",


    @SerializedName("zip_code")
    @Expose
    var zip_code:String="",

    @SerializedName("country")
    @Expose
    var country:String="",

    @SerializedName("state")
    @Expose
    var state:String="",

    @SerializedName("display_address")
    @Expose
    var display_address:List<String>,

    @SerializedName("cross_streets")
    @Expose
    var cross_streets:String="",
)