package com.example.weatherwise.api

import com.example.weatherwise.base.Config.Companion.apiKey
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {

      val newUrl = chain.request().url().newBuilder()
          .addQueryParameter("appid", apiKey)
          .build()

      val newRequest = chain.request().newBuilder()
          .url(newUrl)
          .build()

      return chain.proceed(newRequest)
  }
}

