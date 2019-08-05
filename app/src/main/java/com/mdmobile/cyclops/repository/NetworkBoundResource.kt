package com.mdmobile.cyclops.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiEmptyResponse
import com.mdmobile.cyclops.api.ApiErrorResponse
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.ApiSuccessResponse
import com.mdmobile.cyclops.dataModel.Resource

/**
 * Class responsible for retrieving Resources which are network bound.
 * Retrieves current data from DB while loading from network and updates observers once done
 */

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: ApplicationExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()
    private val dbData = MutableLiveData<ResultType>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbData = loadFromDb()
        result.addSource(dbData) { data ->
            result.removeSource(dbData)
            if (shouldFetch(data)) {
                    fetchFromNetwork(dbData)
            } else {
                result.addSource(dbData) { newData ->
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
        //while loading reattach DB as a source which will dispatch data quicker
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO.execute {
                        saveApiResult(processResponse(response))
                        appExecutors.applicationTread.execute {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse<*> -> {
                    appExecutors.applicationTread.execute {
                        setValue(Resource.success(null))
                    }
                }
                is ApiErrorResponse<*> -> {
                    onFetchFailed()
                    appExecutors.applicationTread.execute {
                        result.postValue(Resource.error(response.errorMessage, null))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    @MainThread
    private fun setValue(newData: Resource<ResultType>) {
        if (newData != result.value) {
            result.value = newData
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

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