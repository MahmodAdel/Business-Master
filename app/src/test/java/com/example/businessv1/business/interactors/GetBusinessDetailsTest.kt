package com.example.businessv1.business.interactors

import com.example.businessv1.business.createBusinessDetails
import com.example.businessv1.business.createBusinessResponse
import com.example.businessv1.business.data.cache.CacheDataSource
import com.example.businessv1.business.data.network.NetworkDataSource
import com.example.businessv1.business.domain.state.DataState
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception

class GetBusinessDetailsTest {
    private val cacheDataSource: CacheDataSource = Mockito.mock(CacheDataSource::class.java)
    private val networkDataSource: NetworkDataSource = Mockito.mock(NetworkDataSource::class.java)
    val useCase = GetBusinessList(cacheDataSource,networkDataSource)


    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()




    @Test
    fun `test Get BusinessDetails returns success`() {
        runBlocking {
            Mockito.`when`(networkDataSource.getDetails("")).thenReturn(
                DataState.Success(createBusinessDetails()))
            val result = useCase.getBusiness("NewYork",20,0)
            Mockito.verify(networkDataSource).getDetails("NewYork")
            Assert.assertEquals(result, DataState.Success(createBusinessDetails()))


        }
    }

    @Test
    fun `test Get BusinessDetails returns fail`() {
        runBlocking {
            Mockito.`when`(networkDataSource.getDetails(""))
                .thenReturn(DataState.Error(exception = Exception("error accured")))
            val result = useCase.getBusinessDetails("")
            Mockito.verify(networkDataSource).getDetails("")
            Assert.assertEquals(result, DataState.Error(exception = Exception("error accured")))
        }
    }
}