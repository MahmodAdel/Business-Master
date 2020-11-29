package com.example.businessv1.frame.datasource.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity

@Dao
interface BusinessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(businessEntity: BusinessCacheEntity): Long

    @Query("SELECT * FROM business")
    suspend fun get(): List<BusinessCacheEntity>

    @Query("DELETE FROM business")
    suspend fun deleteAll()
}