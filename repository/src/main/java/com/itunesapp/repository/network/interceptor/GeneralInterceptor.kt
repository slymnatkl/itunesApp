package com.itunesapp.repository.network.interceptor

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request

class GeneralInterceptor: Interceptor{

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val original = chain.request()
        val originalHttpUrl = original.url

        val urlBuilder: HttpUrl.Builder = originalHttpUrl.newBuilder()

        //Adding default parameters
        //urlBuilder.addQueryParameter("", "")

        val url: HttpUrl = urlBuilder.build()

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder().url(url)

        //Adding header
        //requestBuilder.addHeader("", "");

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}