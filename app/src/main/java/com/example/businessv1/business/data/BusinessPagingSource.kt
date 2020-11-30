package com.example.businessv1.business.data

import androidx.paging.PagingSource
import com.example.businessv1.business.data.cache.CacheDataSource
import com.example.businessv1.business.data.network.NetworkDataSource
import com.example.businessv1.business.domain.model.Business
import retrofit2.HttpException
import java.io.IOException

private const val BUSINESS_STARTING_PAGE_INDEX = 0
private const val BUSINESS_LIMIT = 20

class BusinessPagingSource
constructor(
    private val networkDataSource: NetworkDataSource,
    private val cacheDataSource: CacheDataSource,
    private val query:String
) : PagingSource<Int, Business>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Business> {
        val position = params.key ?: BUSINESS_STARTING_PAGE_INDEX

        return try {
            val response = networkDataSource.getList(query, BUSINESS_LIMIT, position)
            if (position == 0){
                cacheDataSource.deleteAll()
                cacheDataSource.insertList(response.businesses)
            }

            val businessList = response.businesses

            LoadResult.Page(
                data = businessList,
                prevKey = if (position == BUSINESS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (businessList.isEmpty()) null else position + BUSINESS_LIMIT
            )
        } catch (exception: IOException){
          /* if (cacheDataSource.get().isEmpty()){
               LoadResult.Error(exception)
           }else{
               LoadResult.Page(
                   data = cacheDataSource.get(),
                   prevKey = if (position == BUSINESS_STARTING_PAGE_INDEX) null else position - 1,
                   nextKey = if (cacheDataSource.get().isEmpty()) null else position + BUSINESS_LIMIT
               )
           }*/
            LoadResult.Error(exception)

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}