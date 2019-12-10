package com.app.network.cache


interface Listener<T> {

    fun onResponse(response : T)
    fun onError()
}