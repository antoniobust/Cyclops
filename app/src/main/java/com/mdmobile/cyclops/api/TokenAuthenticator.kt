package com.mdmobile.cyclops.api

import android.util.Log
import com.mdmobile.cyclops.util.Logger
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Authenticator class for okHttp.
 * On 401 response will try to request a new token with stored credentials
 * Request limit is 2 -> if requesting a new token with same credential returns 401
 * we can assume credentials are no longer valid.
 * Authenticator only checks "Bearer" requests as "basic" ones are token requests
 */

class TokenAuthenticator(private val apiService: McApiService) : Authenticator {

    private val retryHeader = "RetryCountHeader"
    override fun authenticate(route: Route?, response: Response): Request? {
        Logger.log(TokenAuthenticator::class.java.simpleName,
                "Detected authentication error: ${response.code()} - ${response.request().url()}", Log.VERBOSE)

        return when (isBearerRequest(response)) {
            false -> {
                Logger.log(TokenAuthenticator::class.java.simpleName,
                        "Token request couldn't be retrieved, current credentials are no longer valid, update credentials ", Log.ERROR)
                null
            }
            true -> {
                val count = retryCount(response)
                reAuthenticateReq(response.request(), count + 1)
            }
        }
    }

    //Check whether failed request was a bearer(api call) or basic request(token request)
    private fun isBearerRequest(response: Response): Boolean {
        val authorization = response.request().header("Authorization")
        if (authorization != null) {
            return authorization.startsWith("Bearer: ", true)
        }
        return false
    }

    private fun retryCount(response: Response): Int = response.header(retryHeader)?.toInt() ?: 0

    // Synced method so if multiple request fail dont refresh the same token
    @Synchronized
    private fun reAuthenticateReq(oldReq: Request, retryCount: Int): Request? {
        //too many api re-attempt user must not have enough permission
        if (retryCount > 1) {
            Logger.log(TokenAuthenticator::class.java.simpleName,
                    "Failed to execute  ${oldReq.url()} - retry:($retryCount) - user doesn't have access specified resource", Log.ERROR)
            return null
        }

        Logger.log(TokenAuthenticator::class.java.simpleName,
                "Attempting a token refresh with current credentials - retry:($retryCount)", Log.VERBOSE)

//        val apiService = Retrofit.Builder()
//                .baseUrl(oldReq.url().scheme() + oldReq.url().host())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(McApiService::class.java)

        val newToken = apiService.getAuthToken()

        newToken.let {
            val response = newToken.value
            return if (response is ApiSuccessResponse ) {
                Logger.log(TokenAuthenticator::class.java.simpleName,
                        "Got new token -> resending original request -> (${oldReq.method()}) - ${oldReq.url()}", Log.VERBOSE)
                rewriteRequest(oldReq, retryCount, response.body.token)
            } else {
                Logger.log(TokenAuthenticator::class.java.simpleName,
                        "Token request couldn't be retrieved, current credentials are no longer valid, update credentials ", Log.ERROR)
                null
            }
        }
    }

    private fun rewriteRequest(oldRequest: Request, retryCount: Int, authToken: String?): Request? {
        return oldRequest.newBuilder().header("Authorization", "bearer: $authToken")
                .header(retryHeader, "$retryCount")
                .build()
    }

}
