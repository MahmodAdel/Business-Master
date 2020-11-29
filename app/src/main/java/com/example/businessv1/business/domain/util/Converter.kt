package com.example.businessv1.business.domain.util

import androidx.room.TypeConverter
import com.example.businessv1.frame.datasource.cache.model.CategoryCacheEntity
import com.google.gson.Gson

class Converter {
    @TypeConverter
    fun fromCategoryToGeson(categories : List<CategoryCacheEntity>):String{
        return Gson().toJson(categories)
    }

    @TypeConverter
    fun fromGsonToCategory(categories: String):List<CategoryCacheEntity>{
        return Gson().fromJson(categories, Array<CategoryCacheEntity>::class.java).asList()
    }
}