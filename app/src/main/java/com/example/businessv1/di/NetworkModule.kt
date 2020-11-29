package com.example.businessv1.di

import com.example.businessv1.business.data.network.NetworkDataSource
import com.example.businessv1.business.data.network.NetworkDataSourceImpl
import com.example.businessv1.business.domain.util.NetworkingConstants
import com.example.businessv1.frame.datasource.network.BusinessRetrofitService
import com.example.businessv1.frame.datasource.network.BusinessRetrofitServiceImpl
import com.example.businessv1.frame.datasource.network.mappers.NetworkMapper
import com.example.businessv1.frame.datasource.network.retrofit.BusinessInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {



    @Singleton
    @Provides
    fun providesOkhttpInterceptor(): Interceptor {
        return  Interceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("Authorization", "Bearer 5kWjeDUo7fzXcanqAOQUlchIALfcbnvZ_eNFScrNybAxPrDoAqX06SkTMN2hvmjlXKWeN120l4--vX_RVUA9nShtpDgGqR0egHAFkCFyHADjXfri04_Lo94xqE6-X3Yx")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson,okHttpClient: OkHttpClient):Retrofit.Builder{

        return Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/businesses/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): BusinessInterface {
        return retrofit
            .build()
            .create(BusinessInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideBlogRetrofitService(
        blogRetrofit: BusinessInterface
    ): BusinessRetrofitService {
        return BusinessRetrofitServiceImpl(blogRetrofit)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        businessRetrofitService: BusinessRetrofitService,
        networkMapper: NetworkMapper
    ): NetworkDataSource {
        return NetworkDataSourceImpl(businessRetrofitService, networkMapper)
    }

}