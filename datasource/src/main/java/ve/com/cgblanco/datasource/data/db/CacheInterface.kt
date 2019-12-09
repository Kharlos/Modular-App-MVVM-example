package com.app.network.cache

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * Created by Alireza Rafeezadeh on 6/15/2019.
 */
interface CacheInterface<V> {


    @WorkerThread
    suspend fun  saveItem(item: V)

     fun  load() : LiveData<V>?

    @WorkerThread
    suspend fun  saveList(items : List<V>)

     fun  loadList() : LiveData<List<V>>?

}