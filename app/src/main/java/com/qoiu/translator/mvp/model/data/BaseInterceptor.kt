package com.qoiu.translator.mvp.model.data

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor private constructor() : Interceptor{

    private var responseCode: Int = 0
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        responseCode=response.code()
        return response
    }

    companion object{
        val interceptor: BaseInterceptor
        get()=BaseInterceptor()
    }
}