package com.mdmobile.cyclops.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.mdmobile.cyclops.ApplicationExecutors
import com.mdmobile.cyclops.api.ApiEmptyResponse
import com.mdmobile.cyclops.api.ApiErrorResponse
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.ApiSuccessResponse

/**
 * Class responsible for retrieving data over network and return it
 * as simple Resource -> to be used for data where LiveData is not necessary
 */


//TODO: implement non live data version of network bound resource ...this will be needed for Token requests that are not
// bound to UI changes and other requests that might not be UI related
abstract class NetworkBoundResourceNonLive<RequestType, ResultType>(private val appExecutors: ApplicationExecutors) {

//    val data: ApiResponse<ResultType>
    init {
//        data = createCall()
//        when (response) {
//            is ApiSuccessResponse ->
//            is ApiEmptyResponse ->
//            is ApiErrorResponse ->
//        }
    }

    @MainThread
    abstract fun createCall(): ApiResponse<RequestType>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    abstract fun saveApiResult(item: ResultType)

}