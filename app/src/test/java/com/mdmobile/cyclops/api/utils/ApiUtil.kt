package com.mdmobile.cyclops.api.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mdmobile.cyclops.api.ApiResponse
import com.mdmobile.cyclops.api.ApiSuccessResponse

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(data)

    private fun <T : Any> createCall(response: T) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiSuccessResponse(response)
    } as LiveData<ApiResponse<T>>
}