package com.app.network

import retrofit2.Retrofit
import javax.inject.Inject


class NetworkClient<T> @Inject constructor(val retrofit: Retrofit) {


    fun getRetrofitService(interfaceTyoe : Class<T>) : T {
        return retrofit.create(interfaceTyoe)
    }



}