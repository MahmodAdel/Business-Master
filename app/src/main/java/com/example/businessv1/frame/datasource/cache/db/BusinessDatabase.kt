package com.example.businessv1.frame.datasource.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.businessv1.business.domain.util.Converter
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity


@Database(entities = [BusinessCacheEntity::class ], version = 1)
@TypeConverters(Converter::class)
abstract class BusinessDatabase: RoomDatabase() {

    abstract fun blogDao(): BusinessDao

    companion object{
        val DATABASE_NAME: String = "business_db"
    }


}