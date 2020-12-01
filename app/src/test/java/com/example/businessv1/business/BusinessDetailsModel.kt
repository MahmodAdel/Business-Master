package com.example.businessv1.business

import com.example.businessv1.business.domain.model.BusinessDetails
import com.example.businessv1.business.domain.model.Category
import com.example.businessv1.business.domain.model.Location

fun createBusinessDetails()=BusinessDetails(
    id = "",
    alias = "",
    image_url = "",
    name = "",
    phone = "",
    categories = listOf(createCategoryDetails()),
    rating = 0.0,
    review_count = 0,
    location = createLocationDetails()
)

fun createCategoryDetails()= Category(
    title = "",
    alias = ""
)

fun createLocationDetails()= Location(
    address1 = "",
    address2 = "",
    address3 = "",
    city = "",
    zip_code = "",
    country = "",
    state = "",
    display_address = listOf(""),
    cross_streets = ""
)