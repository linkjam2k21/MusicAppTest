package com.linkjam06.data.apiservice

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiService {

    private var sApiService: ApiServiceInterface? = null

    fun getApiClient(): ApiServiceInterface? {
        if (sApiService == null) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpBuilder = OkHttpClient.Builder()
            okHttpBuilder.addInterceptor(interceptor)
            val okHttpClient = okHttpBuilder.build()

            var retrofit =
                Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

            // creating a service for adapter
            sApiService = retrofit.create(ApiServiceInterface::class.java)
        }

        return sApiService
    }
}