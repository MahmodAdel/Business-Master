package com.example.businessv1.frame.datasource.cache.mappers

import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessResponse
import com.example.businessv1.business.domain.model.Category
import com.example.businessv1.business.domain.util.EntityMapper
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheFavorite
import com.example.businessv1.frame.datasource.cache.model.BusinessResponseCacheEntity
import com.example.businessv1.frame.datasource.cache.model.CategoryCacheEntity
import javax.inject.Inject

class CacheMapper
@Inject
constructor(): EntityMapper<BusinessResponseCacheEntity, BusinessResponse> {
    override fun mapFromEntity(entity: BusinessResponseCacheEntity): BusinessResponse {
        return BusinessResponse(
            businesses = mapFromSubEntityListBusiness(entity.businesses),
            total = entity.total
        )
    }


     fun mapFromSubEntityListBusiness(businesses: List<BusinessCacheEntity>): List<Business> {
        return businesses.map {
            mapFromSubBusinessEntity(it)
        }
    }
    fun mapFavFromSubEntityListBusiness(businesses: List<BusinessCacheFavorite>): List<Business> {
        return businesses.map {
            mapFavFromSubBusinessEntity(it)
        }
    }
     fun mapFromSubEntityListBusinessCategory(categories: List<CategoryCacheEntity>): List<Category> {
        return categories.map {
            mapFromSubBusinessEntityCategory(it)
        }
    }

     fun mapFromSubBusinessEntityCategory(it: CategoryCacheEntity): Category {
        return Category(
            alias = it.alias,
            title = it.title
        )
    }

     fun mapFromSubBusinessEntity(it: BusinessCacheEntity): Business {
        return Business(
            id = it.id,
            image_url = it.image_url,
            name = it.name,
            rating = it.rating,
            category = mapFromSubEntityListBusinessCategory(it.category),
            review_count = it.review_count,
            price = it.price
        )
    }
    fun mapFavFromSubBusinessEntity(it: BusinessCacheFavorite): Business {
        return Business(
            id = it.id,
            image_url = it.image_url,
            name = it.name,
            rating = it.rating,
            category = mapFromSubEntityListBusinessCategory(it.category),
            review_count = it.review_count,
            price = it.price
        )
    }


    override fun mapToEntity(domainModel: BusinessResponse): BusinessResponseCacheEntity {
        return BusinessResponseCacheEntity(
            businesses = mapToSubEntityBusinessListBusiness(domainModel.businesses),
            total = domainModel.total
        )
    }

     fun mapToSubEntityBusinessListBusiness(businessNetworkEntities: List<Business>): List<BusinessCacheEntity> {
        return businessNetworkEntities.map {
            mapToSubEntity(it)
        }
    }

     fun mapToSubEntityBusinessListCategory(categoryNetworks: List<Category>): List<CategoryCacheEntity> {
        return categoryNetworks.map {
            mapToSubEntityCategory(it)
        }
    }

     fun mapToSubEntityCategory(it: Category): CategoryCacheEntity {
        return CategoryCacheEntity(
            alias = it.alias,
            title = it.title
        )
    }

     fun mapToSubEntity(it: Business): BusinessCacheEntity {
        return BusinessCacheEntity(
            id = it.id!!,
            image_url = it.image_url!!,
            name = it.name!!,
            rating = it.rating!!,
            category = mapToSubEntityBusinessListCategory(it.category!!),
            review_count = it.review_count!!,
            price = it.price!!
        )
    }

    fun mapFavToSubEntity(it: Business): BusinessCacheFavorite {
        return BusinessCacheFavorite(
            id = it.id!!,
            image_url = it.image_url!!,
            name = it.name!!,
            rating = it.rating!!,
            category = mapToSubEntityBusinessListCategory(it.category!!),
            review_count = it.review_count!!,
            price = it.price!!
        )
    }

    fun mapFromEntityToList(businessResponseCacheEntity: BusinessResponseCacheEntity):List<Business>{
        return businessResponseCacheEntity.businesses.map {
            mapFromSubBusinessEntity(it)
        }
    }

}