package com.example.businessv1.frame.datasource.network.mappers

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessResponse
import com.example.businessv1.business.domain.model.Category
import com.example.businessv1.business.domain.util.EntityMapper
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkEntity
import com.example.businessv1.frame.datasource.network.model.BusinessNetworkResponse
import com.example.businessv1.frame.datasource.network.model.CategoryNetwork


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


}