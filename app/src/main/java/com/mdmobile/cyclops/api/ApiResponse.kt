package com.mdmobile.cyclops.api

import android.util.Log
import com.mdmobile.cyclops.utils.Logger
import retrofit2.Response
import java.io.IOException


class ApiResponse<T> {
    private val code: Int
    private val body: T?
    private val errorMessage: String?

    val isSuccessful: Boolean
        get() = code in 200..299


    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()?.string()
                } catch (ignored: IOException) {
                    Logger.log(ApiResponse::class.java.simpleName, "error while parsing response", Log.ERROR)
                }

            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }
}