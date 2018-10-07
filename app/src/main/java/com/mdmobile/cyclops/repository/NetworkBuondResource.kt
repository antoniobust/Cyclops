package com.mdmobile.cyclops.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.dataModel.Resource

abstract class NetworkBuondResource<ResultType,RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init{
        result.value = Resource.loading(null)
        val dbData = loadFromDb()
        result.addSource(dbData){
            it ->  re
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}