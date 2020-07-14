package com.victor.lib.coremodel.http

import android.util.Log
import com.victor.lib.coremodel.entity.GankDetailEntity
import com.victor.lib.coremodel.entity.GankRes
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ApiService
 * Author: Victor
 * Date: 2020/7/8 下午 04:49
 * Description: 使用协程进行网络请求
 * -----------------------------------------------------------------
 */
interface ApiService {
    @GET("api/v2/categories/GanHuo")
    suspend fun getGank(): GankRes

    @GET("api/v2/data/category/GanHuo/type/{type}/page/{page}/count/{count}")
    suspend fun getGankDetail(
        @Path("type") type: String,
        @Path("page") page: Int = 0,
        @Path("count") count: Int?
    ): GankDetailEntity

    @GET("api/v2/data/category/Girl/type/Girl/page/{page}/count/{count}")
    suspend fun getFuliList(
        @Path("page") page: Int = 0,
        @Path("count") count: Int?
    ): GankDetailEntity

    companion object {
        const val TAG = "ApiService"
        const val GANK_HOST = "https://gank.io/"
        fun create(baseUrl: String): ApiService {

            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(initLogInterceptor())
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        private fun initLogInterceptor(): HttpLoggingInterceptor {

            val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e(TAG, message)
                }
            })

            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return interceptor
        }
    }


}