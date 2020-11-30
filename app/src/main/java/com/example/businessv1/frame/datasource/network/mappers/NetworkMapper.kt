package com.example.businessv1.frame.datasource.network.mappers

import com.example.businessv1.business.domain.model.*
import com.example.businessv1.business.domain.util.EntityMapper
import com.example.businessv1.frame.datasource.network.model.*


import javax.inject.Inject
class NetworkMapper
@Inject
constructor(): EntityMapper<BusinessNetworkResponse, BusinessResponse> {
    override fun mapFromEntity(entity: BusinessNetworkResponse): BusinessResponse {
        return BusinessResponse(
            businesses = mapFromSubEntityListBusiness(entity.businesses),
            total = entity.total
        )
    }


    private fun mapFromSubEntityListBusiness(businesses: List<BusinessNetworkEntity>): List<Business> {
        return businesses.mapNotNull {
            mapFromSubBusinessEntity(it)
        }
    }
    private fun mapFromSubEntityListBusinessCategory(categories: List<CategoryNetwork>): List<Category> {

        return categories.mapNotNull {
            mapFromSubBusinessEntityCategory(it)
        }
    }

    private fun mapFromSubBusinessEntityCategory(it: CategoryNetwork): Category {
        return Category(
            alias = it.alias?:"",
            title = it.title?:""
        )
    }

    public fun mapFromSubBusinessEntity(it: BusinessNetworkEntity): Business {
        return Business(
            id = it.id,
            image_url = it.image_url?:"",
            name = it.name?:"",
            rating = it.rating?:"",
            category = mapFromSubEntityListBusinessCategory(it.category),
            review_count = it.review_count,
            price = it.price?:""
        )
    }


    override fun mapToEntity(domainModel: BusinessResponse): BusinessNetworkResponse {
        return BusinessNetworkResponse(
            businesses = mapToSubEntityBusinessListBusiness(domainModel.businesses),
            total = domainModel.total
        )
    }

    private fun mapToSubEntityBusinessListBusiness(businessNetworkEntities: List<Business>): List<BusinessNetworkEntity> {
        return businessNetworkEntities.mapNotNull {
            mapToSubEntity(it)
        }
    }

    private fun mapToSubEntityBusinessListCategory(categoryNetworks: List<Category>): List<CategoryNetwork> {
        return categoryNetworks.mapNotNull {
            mapToSubEntityCategory(it)
        }
    }

    private fun mapToSubEntityCategory(it: Category): CategoryNetwork {
        return CategoryNetwork(
            alias = it.alias?:"",
            title = it.title?:""
        )
    }

    private fun mapToSubEntity(it: Business): BusinessNetworkEntity {
        return BusinessNetworkEntity(
            id = it.id,
            image_url = it.image_url,
            name = it.name,
            rating = it.rating,
            category = mapToSubEntityBusinessListCategory(it.category),
            review_count = it.review_count,
            price = it.price
        )
    }

    public fun mapFromEntityToList(businessResponseCacheEntity: BusinessNetworkResponse):List<Business>{
        return businessResponseCacheEntity.businesses.mapNotNull {
            mapFromSubBusinessEntity(it)
        }
    }

    fun mapFromDetailsEntity(details: BusinessNetworkDetails): BusinessDetails {
        return BusinessDetails(
            id = details.id?:"",
            alias = details.alias?:"",
            image_url = details.image_url?:"",
            name = details.name?:"",
            phone = details.phone?:"",
            categories = mapFromSubEntityListBusinessCategory(details.categories),
            rating = details.rating,
            review_count = details.review_count,
            location = mapFromSubEntityListBusinessLocation(details.location)?:Location()

        )
    }

    private fun mapFromSubEntityListBusinessLocation(location: BusinessNetworkDetailsLocation): Location {
        return if (location != null)
            Location(
                address1 = location.address1?:"",
                address2 = location.address2?:"",
                address3 = location.address3?:"",
                city = location.city?:"",
                zip_code = location.zip_code?:"",
                country = location.country?:"",
                state = location.state?:"",
                display_address = location.display_address,
                cross_streets = location.cross_streets?:""
            )
        else
            Location()
    }


}