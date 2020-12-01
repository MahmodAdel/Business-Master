package com.example.businessv1.business.interactors

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.businessv1.business.createBusinessResponse
import com.example.businessv1.business.data.cache.CacheDataSource
import com.example.businessv1.business.data.network.NetworkDataSource
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessResponse
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.frame.datasource.network.BusinessRetrofitService
import com.example.businessv1.frame.datasource.network.BusinessRetrofitServiceImpl
import com.example.businessv1.frame.datasource.network.retrofit.BusinessInterface
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.Exception


class GetBusinessListTest {
    private val cacheDataSource: CacheDataSource=mock(CacheDataSource::class.java)
    private val networkDataSource: NetworkDataSource=mock(NetworkDataSource::class.java)
    val useCase = GetBusinessList(cacheDataSource,networkDataSource)


    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()




    @Test
    fun `test Get Business returns success`() {
        runBlocking {
            `when`(networkDataSource.getList("NewYork",20,0)).thenReturn(
                DataState.Success(createBusinessResponse()))
            val result = useCase.getBusiness("NewYork",20,0)
            verify(networkDataSource).getList("NewYork",20,0)
            Assert.assertEquals(result, DataState.Success(createBusinessResponse()))


        }
    }

    @Test
    fun `test Get Business returns fail`() {
        runBlocking {
            `when`(networkDataSource.getList("NewYork",20,0)).thenReturn(DataState.Error(exception = Exception("error accured")))
            val result = useCase.getBusiness("NewYork",20,0)
            verify(networkDataSource).getList("NewYork",20,0)
            Assert.assertEquals(result, DataState.Error(exception = Exception("error accured")))


        }
    }

}