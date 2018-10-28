package com.mdmobile.cyclops.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.ApiSuccessResponse
import com.mdmobile.cyclops.dataModel.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(val appExecutors: ApplicationExecutors) {

    private val liveDataManager = MediatorLiveData<Resource<ResultType>>()

    init {
        liveDataManager.value = Resource.loading(null)
        val dbData = loadFromDb()
        liveDataManager.addSource(dbData) { data ->
            liveDataManager.removeSource(dbData)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbData)
            } else {
                liveDataManager.addSource(dbData) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        /**
         * 1. create api call
         * 2. add API source to mediatorLiveData
         * 3. remove any source from mediator
         * 4. check what response type we have from API
         *      - if Success ->  save results in DB on disk executor and add new source to load data latest from DB
         *      - if empty response -> do not save anything ... just reload what we had
         *      - if response is error -> execute on fetch failed  and set new soruce with resource error
         */
        val apiResponse = createCall()
//        //while loading reattach DB as a source which will dispatch data quicker
//        liveDataManager.addSource(dbSource){ newData ->
//            setValue(Resource.loading(newData))
//        }

//        liveDataManager.addSource(apiResponse) { response ->
//            liveDataManager.removeSource(apiResponse)
//            liveDataManager.removeSource(dbSource)
//
//            when (apiResponse) {
//                is ApiSuccessResponse<RequestType> -> {
//                    appExecutors.diskIO.execute {
//                        saveApiResult(processResponse(response))
//                        appExecutors.applicationTread.execute {
//                            liveDataManager.addSource(loadFromDb()) { newData ->
//                                setValue(Resource.success(newData))
//                            }
//                        }
//                    }
//                }
//                is ApiEmptyResponse<*> -> {
//                    appExecutors.applicationTread.execute {
//                        setValue(Resource.success())
//                    }
//                }
//                is ApiErrorResponse<*> -> {
//
//                }
//            }
//        }
    }

    protected open fun onFetchFailed() {}

    @MainThread
    private fun setValue(newData: Resource<ResultType>) {
        if (newData != liveDataManager.value) {
            liveDataManager.value = newData
        }
    }

    fun asLiveData() = liveDataManager as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveApiResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}