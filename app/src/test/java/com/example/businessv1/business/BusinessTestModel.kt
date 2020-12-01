package com.example.businessv1.business

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessResponse
import com.example.businessv1.business.domain.model.Category
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkEntity
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse
import com.example.businessv1.frame.datasource.network.model.CategoryNetwork
import java.util.*

fun createBusinessResponse()=BusinessResponse(
    businesses = Arrays.asList(
        com.example.businessv1.business.createBusiness()),
    total = 0
)


fun createBusiness()=Business(
        id = "",
        image_url = "",
        name = "",
        rating = "",
        category = Arrays.asList(createCategory()),
        review_count = 0,
        price = ""

)

fun createCategory()=Category(
    title = "",
    alias = ""
)