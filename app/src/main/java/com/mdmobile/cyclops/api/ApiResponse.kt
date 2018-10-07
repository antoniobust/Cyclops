package com.mdmobile.cyclops.api

import retrofit2.Response

/**
 * Wrapper for API response.
 * Represent a failed or successful/empty response API
 */

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "Unknown API error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T?> {
            return if (response.isSuccessful) {
                if (response.body() == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(response.body())
                }
            } else {
                val msg = response.errorBody()?.toString()
                val errorMessage = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMessage ?: "Unknown API error message")
            }
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()