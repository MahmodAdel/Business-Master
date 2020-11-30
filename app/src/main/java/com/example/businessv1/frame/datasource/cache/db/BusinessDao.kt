package com.example.businessv1.frame.datasource.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheEntity
import com.example.businessv1.frame.datasource.cache.model.BusinessCacheFavorite

@Dao
interface BusinessDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(businessEntity: BusinessCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(businessFav: BusinessCacheFavorite): Long

    @Query("SELECT * FROM business")
    suspend fun get(): List<BusinessCacheEntity>

    @Query("DELETE FROM business")
    suspend fun deleteAll()

    @Query("SELECT * FROM businessfav")
    suspend fun getFav(): List<BusinessCacheFavorite>

    @Query("delete from businessfav where id =:businessId")
    fun deleteBusiness(businessId: String?)
}